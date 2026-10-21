package ch.daniil.gymplan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlanService {
    private final GeminiClient geminiClient;
    private final PromptBuilder promptBuilder;
    private final boolean geminiEnabled;
    public PlanService(GeminiClient geminiClient,
                       PromptBuilder promptBuilder,
                       @Value("${gemini.enabled:false}") boolean geminiEnabled) {
        this.geminiClient = geminiClient;
        this.promptBuilder = promptBuilder;
        this.geminiEnabled = geminiEnabled;
        System.out.println("DEBUG gemini enabled = " + geminiEnabled);
    }
    public String generateRawPlan(PlanRequest request) {
        if (!geminiEnabled) {
            return null;
        }
        return geminiClient.generate(promptBuilder.build(request));
    }

    public Plan createPlan(PlanRequest planRequest) {
        Plan plan = new Plan();
        Exercise bench_press = new Exercise("Bench press", 2, "8-10", "120");
        Exercise squat = new Exercise("Squat", 4, "6-8", "150");
        for(int i = 1; i < planRequest.getDaysPerWeek(); i++){
            Day day = new Day();
            day.setLabel("Day " + i);
            day.setFocus("Back");
            day.getListOfExercises().add(bench_press);
            day.getListOfExercises().add(squat);
            plan.getDays().add(day);
        }
        return plan;
    }
}
