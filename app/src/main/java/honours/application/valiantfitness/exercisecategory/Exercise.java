package honours.application.valiantfitness.exercisecategory;

public class Exercise {

    String name;
    int repetitionGoal;
    int repetitionCompleted;
    int weight;
    String group;

    public Exercise(String name, int repetitionGoal, int weight, String group) {
        this.name = name;
        this.repetitionGoal = repetitionGoal;
        this.weight = weight;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRepetitionGoal() {
        return repetitionGoal;
    }

    public void setRepetitionGoal(int repetitionGoal) {
        this.repetitionGoal = repetitionGoal;
    }

    public int getRepetitionCompleted() {
        return repetitionCompleted;
    }

    public void setRepetitionCompleted(int repetitionCompleted) {
        this.repetitionCompleted = repetitionCompleted;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
