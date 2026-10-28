package ch.daniil.gymplan;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Daily calorie and macro estimates. Plain Java math (no AI), so it is predictable and unit-testable.
 * Estimates only: real needs differ by roughly 10 percent from person to person.
 */
@Component
public class NutritionCalculator {

    private static final int MIN_KCAL_MALE = 1500;
    private static final int MIN_KCAL_FEMALE = 1200;
    private static final double KCAL_PER_KG_BODY_WEIGHT = 7700;

    private final MessageSource messages;

    public NutritionCalculator(MessageSource messages) {
        this.messages = messages;
    }

    public NutritionTargets calculate(PlanRequest r) {
        Locale locale = LocaleContextHolder.getLocale();
        boolean male = "male".equals(r.getSex());
        double weight = r.getWeightKg();
        String goal = r.getGoal();

        // With a body fat value: Katch-McArdle (lean mass). Without: Mifflin-St Jeor.
        boolean hasBodyFat = r.getBodyFatPercent() != null;
        double leanMass = hasBodyFat ? weight * (1 - r.getBodyFatPercent() / 100.0) : weight;
        double bmr = hasBodyFat
                ? 370 + 21.6 * leanMass
                : 10 * weight + 6.25 * r.getHeightCm() - 5 * r.getAge() + (male ? 5 : -161);
        double maintenance = bmr * activityFactor(r.getDaysPerWeek());

        double target = maintenance * (1 + calorieChange(goal));
        StringBuilder note = new StringBuilder();

        // Safety limits: cap the daily deficit and never go below a minimum intake.
        double maxDeficit = maxDeficit(goal);
        if (maxDeficit > 0 && maintenance - target > maxDeficit) {
            target = maintenance - maxDeficit;
        }
        int floor = male ? MIN_KCAL_MALE : MIN_KCAL_FEMALE;
        if (target < floor) {
            target = floor;
            note.append(messages.getMessage("nutrition.note.floor", new Object[]{floor}, locale)).append(' ');
        }
        if ("aggressive_cut".equals(goal)) {
            note.append(messages.getMessage("nutrition.note.aggressive", null, locale));
        }

        // Protein per kg of lean mass (if body fat is known) or of body weight. Fat: 25% of calories, min 0.6 g/kg.
        double protein = hasBodyFat ? leanMass * proteinPerKgLean(goal) : weight * proteinPerKg(goal);
        double fat = Math.max(0.25 * target / 9, 0.6 * weight);
        double carbs = Math.max(0, (target - protein * 4 - fat * 9) / 4);

        double weeklyChange = Math.round((target - maintenance) * 7 / KCAL_PER_KG_BODY_WEIGHT * 100) / 100.0;

        return new NutritionTargets(
                roundTo(maintenance, 10),
                roundTo(target, 10),
                (int) Math.round(protein),
                (int) Math.round(fat),
                (int) Math.round(carbs),
                weeklyChange,
                note.toString().trim());
    }

    private static double activityFactor(int trainingDaysPerWeek) {
        if (trainingDaysPerWeek <= 3) {
            return 1.375;
        }
        return trainingDaysPerWeek <= 5 ? 1.55 : 1.725;
    }

    // Share of maintenance calories: negative = deficit, positive = surplus.
    private static double calorieChange(String goal) {
        return switch (goal) {
            case "cut" -> -0.20;
            case "aggressive_cut" -> -0.30;
            case "lean_bulk" -> 0.07;
            case "bulk" -> 0.15;
            default -> 0.0;
        };
    }

    private static double maxDeficit(String goal) {
        return switch (goal) {
            case "cut" -> 750;
            case "aggressive_cut" -> 1000;
            default -> 0;
        };
    }

    private static double proteinPerKg(String goal) {
        return switch (goal) {
            case "cut" -> 2.2;
            case "aggressive_cut" -> 2.6;
            case "lean_bulk" -> 2.0;
            case "bulk", "strength" -> 1.8;
            default -> 1.6;
        };
    }

    private static double proteinPerKgLean(String goal) {
        return switch (goal) {
            case "cut" -> 2.6;
            case "aggressive_cut" -> 3.0;
            case "lean_bulk" -> 2.2;
            case "bulk", "strength" -> 2.0;
            default -> 1.8;
        };
    }

    private static int roundTo(double value, int step) {
        return (int) (Math.round(value / step) * step);
    }
}
