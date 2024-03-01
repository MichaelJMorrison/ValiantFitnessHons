package honours.application.valiantfitness.exercisedata;

public class ExerciseSetData {

    private double weight;
    private int rep;

    public ExerciseSetData(double weight, int rep) {
        this.weight = weight;
        this.rep = rep;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public ExerciseSetData() {
        this.rep = 0;
        this.weight = 0;
    }

    @Override
    public String toString() {
        return "exerciseSetData{" +
                "weight=" + weight +
                ", rep=" + rep +
                '}';
    }
}
