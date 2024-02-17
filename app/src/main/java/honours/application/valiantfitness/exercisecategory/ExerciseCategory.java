package honours.application.valiantfitness.exercisecategory;

public class ExerciseCategory {

    String name;
    String Group;

    int Image; // Might work for image?

    public ExerciseCategory(String name, String group, int image) {
        this.name = name;
        Group = group;
        Image = image;
    }

    public ExerciseCategory(String name, String group) {
        this.name = name;
        Group = group;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
