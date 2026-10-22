package ch.daniil.gymplan;

import java.util.ArrayList;

public class Day {
    private String label;
    private String focus;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }
}
