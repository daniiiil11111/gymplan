package ch.daniil.gymplan;

import java.util.ArrayList;

public class Day {
    private String label;
    private String focus;
    private ArrayList<Exercise> listOfExercises = new ArrayList<>();
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

    public ArrayList<Exercise> getListOfExercises() {
        return listOfExercises;
    }

    public void setListOfExercises(ArrayList<Exercise> listOfExercises) {
        this.listOfExercises = listOfExercises;
    }
}
