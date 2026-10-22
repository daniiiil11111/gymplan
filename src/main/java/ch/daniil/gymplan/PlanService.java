package ch.daniil.gymplan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService {
    private final GeminiClient geminiClient;
    private final PromptBuilder promptBuilder;
    private final PlanChecker planChecker;
    private final JsonMapper jsonMapper;
    private final boolean geminiEnabled;

    public PlanService(GeminiClient geminiClient,
                       PromptBuilder promptBuilder,
                       PlanChecker planChecker,
                       JsonMapper jsonMapper,
                       @Value("${gemini.enabled:false}") boolean geminiEnabled) {
        this.geminiClient = geminiClient;
        this.promptBuilder = promptBuilder;
        this.planChecker = planChecker;
        this.jsonMapper = jsonMapper;
        this.geminiEnabled = geminiEnabled;
    }
    public Plan generatePlan(PlanRequest request) {
        if (!geminiEnabled) {
            return createPlan(request);   // offline fallback: your fake plan
        }

        String prompt = promptBuilder.build(request);
        int expectedDays = request.getDaysPerWeek();
        List<String> lastProblems = List.of();

        for (int attempt = 1; attempt <= 3; attempt++) {
            // Client errors (quota, overload, timeout) go straight to the user, with no retry.
            String text = geminiClient.generate(prompt);
            try {
                Plan plan = parse(text);
                List<String> problems = planChecker.check(plan, expectedDays);
                if (problems.isEmpty()) {
                    return plan;
                }
                lastProblems = problems;
            } catch (GeminiException e) {
                lastProblems = List.of("answer could not be parsed");
            }
        }
        System.out.println("Plan rejected after 2 attempts: " + lastProblems);
        throw new GeminiException("We could not create a valid plan. Please try again.");
    }
    private Plan parse(String json) {
        try {
            return jsonMapper.readValue(json, Plan.class);
        } catch (JacksonException e) {
            throw new GeminiException("The plan could not be read");
        }
    }
    public Plan createPlan(PlanRequest planRequest) {
        Plan plan = new Plan();
        Exercise bench_press = new Exercise("Bench press", 2, "8-10", "120");
        Exercise squat = new Exercise("Squat", 4, "6-8", "150");
        for(int i = 1; i < planRequest.getDaysPerWeek(); i++){
            Day day = new Day();
            day.setLabel("Day " + i);
            day.setFocus("Back");
            day.getExercises().add(bench_press);
            day.getExercises().add(squat);
            plan.getDays().add(day);
        }
        return plan;
    }
}
