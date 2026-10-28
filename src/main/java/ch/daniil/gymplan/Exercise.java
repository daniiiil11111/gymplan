package ch.daniil.gymplan;

public class Exercise {
    private String muscle;

    public Integer getRir() {
        return rir;
    }

    public void setRir(Integer rir) {
        this.rir = rir;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    private Integer rir;
    private String name;
    private Integer sets;
    private String reps;
    private String restSeconds;
    public Exercise(String name, Integer sets, String reps, String restSeconds) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.restSeconds = restSeconds;
    }
    public Exercise(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getRestSeconds() {
        return restSeconds;
    }

    public void setRestSeconds(String restSeconds) {
        this.restSeconds = restSeconds;
    }
}
