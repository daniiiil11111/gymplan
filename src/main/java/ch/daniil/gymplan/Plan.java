package ch.daniil.gymplan;

import java.util.ArrayList;

public class Plan {
    private String progression;
    private String deload;

    public String getProgression() {
        return progression;
    }

    public void setProgression(String progression) {
        this.progression = progression;
    }

    public String getDeload() {
        return deload;
    }

    public void setDeload(String deload) {
        this.deload = deload;
    }

    private String summary;
    private ArrayList<Day> days = new ArrayList<>();

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }
}
