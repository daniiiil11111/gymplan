package ch.daniil.gymplan;

/** Thrown when a Gemini call fails. The message is safe to show to the user (never contains the key). */
public class GeminiException extends RuntimeException {

    public GeminiException(String message) {
        super(message);
    }
}