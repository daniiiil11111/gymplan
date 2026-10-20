package ch.daniil.gymplan;

public class PlanRequest {
    private Integer age;
    private String sex;
    private Integer heightCm;
    private Integer weightKg;
    private String experience;
    private String goal;
    private String equipment;
    private Integer daysPerWeek;
    private Integer minutesPerSession;
    private String splitPreference;
    private String desiredExercises;
    private String focusMuscles;
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
