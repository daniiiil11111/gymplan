package ch.daniil.gymplan;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Weekly sets per muscle group, computed from the plan (never trusted from the AI). */
@Component
public class VolumeCalculator {

    // Fixed display order. The muscle names are the same keys as in the JSON schema and the message files.
    private static final List<String> ORDER = List.of(
            "chest", "back", "shoulders", "biceps", "triceps",
            "quads", "hamstrings", "glutes", "calves", "core");

    public Map<String, Integer> weeklySets(Plan plan) {
        Map<String, Integer> totals = new LinkedHashMap<>();
        for (String muscle : ORDER) {
            totals.put(muscle, 0);
        }
        for (Day day : plan.getDays()) {
            for (Exercise exercise : day.getExercises()) {
                totals.merge(exercise.getMuscle(), exercise.getSets(), Integer::sum);
            }
        }
        totals.values().removeIf(sets -> sets == 0);
        return totals;
    }
}
