package ch.daniil.gymplan;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PlanChecker {
    public ArrayList<String> check(Plan plan, int expectedDays){
        ArrayList<String> problems = new ArrayList<>();

        if (plan == null || plan.getDays() == null){
            problems.add("Days not found");
            return problems;
        }
        if (plan.getDays().size() != expectedDays){
            problems.add("Expected " + expectedDays + " songs but got " + plan.getDays().size());
        }
        for (int i = 0; i < plan.getDays().size(); i++){
            Day day = plan.getDays().get(i);
            int number = i + 1;
            if (day.getLabel() == null || day.getFocus() == null){
                problems.add("Day " + number + " not found");
            }
            for (int k = 0; k < day.getExercises().size(); k++){
                Exercise exercise = day.getExercises().get(k);
                int number2 = k + 1;
                if (exercise == null){
                    problems.add("Day " + number + ", exercise " + number2 + " not found");
                }
                if (exercise.getName().length() > 80){
                    problems.add("Day " + number + ", exercise " + number2 + " name too long");
                }
                if (exercise.getSets() == null){
                    problems.add("Day " + number + ", exercise " + number2 + ", exercise sets not found");
                }
                if (exercise.getSets() < 1 || exercise.getSets() > 10){
                    problems.add("Day " + number + ", exercise " + number2 +  ", exercise sets must be between 1 and 10");
                }
                if (exercise.getReps() == null){
                    problems.add("Day " + number + ", exercise " + number2 + ", exercise reps not found");
                }
                if (exercise.getReps().length() > 30){
                    problems.add("Day " + number+ ", exercise " + number2 + ", exercise reps too long");
                }
                if(exercise.getRestSeconds() == null || exercise.getRestSeconds().length() == 0 || exercise.getRestSeconds().length() > 30){
                    problems.add("Day " + number+ ", exercise " + number2 + ", exercise rest seconds dont exist or are too long");
                }
            }
        } return problems;
    }
}
