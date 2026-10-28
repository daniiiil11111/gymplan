package ch.daniil.gymplan;

/** Daily calorie and macro estimates for one person. Plain getters so Thymeleaf can read them. */
public class NutritionTargets {

    private final int maintenanceCalories;
    private final int targetCalories;
    private final int proteinGrams;
    private final int fatGrams;
    private final int carbGrams;
    private final double weeklyChangeKg;
    private final String note;

    public NutritionTargets(int maintenanceCalories, int targetCalories,
                            int proteinGrams, int fatGrams, int carbGrams,
                            double weeklyChangeKg, String note) {
        this.maintenanceCalories = maintenanceCalories;
        this.targetCalories = targetCalories;
        this.proteinGrams = proteinGrams;
        this.fatGrams = fatGrams;
        this.carbGrams = carbGrams;
        this.weeklyChangeKg = weeklyChangeKg;
        this.note = note;
    }

    public int getMaintenanceCalories() { return maintenanceCalories; }
    public int getTargetCalories() { return targetCalories; }
    public int getProteinGrams() { return proteinGrams; }
    public int getFatGrams() { return fatGrams; }
    public int getCarbGrams() { return carbGrams; }
    /** Signed: negative = expected weight loss per week, positive = gain. */
    public double getWeeklyChangeKg() { return weeklyChangeKg; }
    public double getWeeklyChangeAbsKg() { return Math.abs(weeklyChangeKg); }
    public String getNote() { return note; }
}
