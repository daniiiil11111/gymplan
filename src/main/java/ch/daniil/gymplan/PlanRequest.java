package ch.daniil.gymplan;

import jakarta.validation.constraints.*;

public class PlanRequest {
    @NotNull(message = "Please enter your age")
    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 80, message = "Age must be at most 80")
    private Integer age;

    @NotBlank(message = "Please choose your sex")
    @Pattern(regexp = "male|female", message = "Please choose a valid option")
    private String sex;

    @NotNull(message = "Please enter your height")
    @Min(value = 120, message = "Height must be at least 120 cm")
    @Max(value = 230, message = "Height must be at most 230 cm")
    private Integer heightCm;

    @NotNull(message = "Please enter your weight")
    @Min(value = 35, message = "Weight must be at least 35 kg")
    @Max(value = 250, message = "Weight must be at most 250 kg")
    private Integer weightKg;

    @NotBlank(message = "Please choose your experience level")
    @Pattern(regexp = "beginner|intermediate|advanced", message = "Please choose a valid experience level")
    private String experience;

    @NotBlank(message = "Please choose a goal")
    @Pattern(regexp = "muscle_gain|strength|fat_loss|general_fitness", message = "Please choose a valid goal")
    private String goal;

    @NotBlank(message = "Please choose your equipment")
    @Pattern(regexp = "full_gym|dumbbells|bodyweight", message = "Please choose valid equipment")
    private String equipment;

    @NotNull(message = "Please enter the days per week")
    @Min(value = 1, message = "Train at least 1 day per week")
    @Max(value = 7, message = "A week has only 7 days")
    private Integer daysPerWeek;

    @NotNull(message = "Please enter the minutes per session")
    @Min(value = 20, message = "A session must be at least 20 minutes")
    @Max(value = 180, message = "A session can be at most 180 minutes")
    private Integer minutesPerSession;

    @NotBlank(message = "Please choose a split")
    @Pattern(regexp = "ai_decides|full_body|upper_lower|push_pull_legs", message = "Please choose a valid split")
    private String splitPreference;

    @Size(max = 200, message = "Desired exercises can be at most 200 characters")
    private String desiredExercises;

    @Size(max = 200, message = "Focus muscles can be at most 200 characters")
    private String focusMuscles;

    @NotBlank(message = "Please choose a language")
    @Pattern(regexp = "en|de", message = "Please choose a valid language")
    private String language;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(Integer heightCm) {
        this.heightCm = heightCm;
    }

    public Integer getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Integer weightKg) {
        this.weightKg = weightKg;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Integer getDaysPerWeek() {
        return daysPerWeek;
    }

    public void setDaysPerWeek(Integer daysPerWeek) {
        this.daysPerWeek = daysPerWeek;
    }

    public Integer getMinutesPerSession() {
        return minutesPerSession;
    }

    public void setMinutesPerSession(Integer minutesPerSession) {
        this.minutesPerSession = minutesPerSession;
    }

    public String getSplitPreference() {
        return splitPreference;
    }

    public void setSplitPreference(String splitPreference) {
        this.splitPreference = splitPreference;
    }

    public String getDesiredExercises() {
        return desiredExercises;
    }

    public void setDesiredExercises(String desiredExercises) {
        this.desiredExercises = desiredExercises;
    }

    public String getFocusMuscles() {
        return focusMuscles;
    }

    public void setFocusMuscles(String focusMuscles) {
        this.focusMuscles = focusMuscles;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
