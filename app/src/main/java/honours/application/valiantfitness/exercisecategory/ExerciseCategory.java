package honours.application.valiantfitness.exercisecategory;

import java.util.List;

public class ExerciseCategory {

    String name;
    String group;

    String mode;

    List<Exercise> exercises;
    int Image; // Might work for image?

    public ExerciseCategory(String name, String group, int image,String mode) {
        this.name = name;
        this.group = group;
        this.Image = image;
        this.mode = mode;
    }

    public ExerciseCategory(String name, String group) {
        this.name = name;
        this.group = group;

    }

    public ExerciseCategory(String name, String group, String mode) {
        this.name = name;
        this.group = group;
        this.mode = mode;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
