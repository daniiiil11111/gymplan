package ch.daniil.gymplan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class GeminiClient {

    private final RestClient restClient;
    private final String apiKey;
    private final String model;

    // The ":" default means "empty if the property is missing", so the app still starts
    // without a key. The check happens in generate(), with a clear message.
    public GeminiClient(@Value("${gemini.api-key:}") String apiKey,
                        @Value("${gemini.model:}") String model) {
        this.apiKey = apiKey;
        this.model = model;

        // Connect timeout lives on the HttpClient, read timeout on the request factory.
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(Duration.ofSeconds(30));

        this.restClient = RestClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .requestFactory(factory)
                .build();
        System.out.println("DEBUG key set = " + !apiKey.isBlank() + ", model = " + model);
    }

    /** Sends the prompt and returns the text of Gemini's answer (a JSON string in JSON mode). */
    public String generate(String prompt) {
        if (apiKey.isBlank()) {
            throw new GeminiException("GEMINI_API_KEY is not set");
        }
        if (model.isBlank()) {
            throw new GeminiException("gemini.model is not set");
        }

        // The mapper turns these Maps and Lists into JSON and escapes the prompt for us.
        Map<String, Object> body = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))),
                "generationConfig", Map.of(
                        "responseMimeType", "application/json",
                        "responseJsonSchema", planSchema()));

        Map<?, ?> response;
        try {
            response = restClient.post()
                    .uri("/v1beta/models/{model}:generateContent", model)
                    .header("x-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(Map.class);
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            System.out.println("GEMINI ERROR " + status + ": " + e.getResponseBodyAsString());
            if (status == 429) {
                throw new GeminiException("Gemini quota or rate limit reached. Wait a minute and try again.");
            }
            throw new GeminiException("Gemini returned HTTP " + status);
        } catch (RestClientException e) {
            throw new GeminiException("Could not reach Gemini (timeout or network problem)");
        }

        return extractText(response);
    }

    // candidates -> item 0 -> content -> parts -> item 0 -> text
    private String extractText(Map<?, ?> response) {
        try {
            List<?> candidates = (List<?>) response.get("candidates");
            Map<?, ?> first = (Map<?, ?>) candidates.get(0);
            Map<?, ?> content = (Map<?, ?>) first.get("content");
            List<?> parts = (List<?>) content.get("parts");
            Map<?, ?> part = (Map<?, ?>) parts.get(0);
            Object text = part.get("text");
            if (text instanceof String s && !s.isBlank()) {
                return s;
            }
        } catch (RuntimeException e) {
            // A missing level (null, wrong type, empty list) ends up here.
        }
        throw new GeminiException("Gemini returned an unexpected answer");
    }

    // The same tree as Plan -> Day -> Exercise. Key names must equal your Java property names.
    // If your Exercise.reps is an Integer, change "reps" below to "integer".
    private Map<String, Object> planSchema() {
        Map<String, Object> exercise = Map.of(
                "type", "object",
                "properties", Map.of(
                        "name", Map.of("type", "string"),
                        "sets", Map.of("type", "integer"),
                        "reps", Map.of("type", "string"),
                        "restSeconds", Map.of("type", "integer")),
                "required", List.of("name", "sets", "reps", "restSeconds"));

        Map<String, Object> day = Map.of(
                "type", "object",
                "properties", Map.of(
                        "label", Map.of("type", "string"),
                        "focus", Map.of("type", "string"),
                        "exercises", Map.of("type", "array", "items", exercise)),
                "required", List.of("label", "focus", "exercises"));

        return Map.of(
                "type", "object",
                "properties", Map.of(
                        "summary", Map.of("type", "string"),
                        "days", Map.of("type", "array", "items", day)),
                "required", List.of("summary", "days"));
    }
}