package honours.application.valiantfitness.exercisecategory;

public class Exercise {

    String name;
    int repetitionGoal;
    int repetitionCompleted;
    int weight;
    String description;

    String Group;
    String Mode;

    public Exercise(String name, int repetitionGoal, int repetitionCompleted, int weight, String group, String mode) {
        this.name = name;
        this.repetitionGoal = repetitionGoal;
        this.repetitionCompleted = repetitionCompleted;
        this.weight = weight;
        this.Group = group;
        this.Mode = mode;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
