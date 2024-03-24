package honours.application.valiantfitness.exercisecategory;

import java.util.List;

public class ExerciseCategory {

    String name;
    String group;

    String mode;

    String equipmentType;

    WorkoutPlan workoutPlan;

    List<Exercise> exercises;
    String Image; // Might work for image?

    public ExerciseCategory(String name, String group, String image,String mode, WorkoutPlan workoutPlan) {
        this.name = name;
        this.group = group;
        this.Image = image;
        this.mode = mode;
        this.workoutPlan = workoutPlan;
    }

    public ExerciseCategory(String name, String group, String image,String mode, String equipmentType) {
        this.name = name;
        this.group = group;
        this.Image = image;
        this.mode = mode;
        this.equipmentType = equipmentType;
    }

    public ExerciseCategory(String name, String group) {
        this.name = name;
        this.group = group;

    }

    public ExerciseCategory(String name, String group, String mode, String equipmentType) {
        this.name = name;
        this.group = group;
        this.mode = mode;
        this.equipmentType = equipmentType;
    }

    public ExerciseCategory(String name, String group, String mode) {
        this.name = name;
        this.group = group;
        this.mode = mode;

    }

    public ExerciseCategory(String name, String group, String mode, WorkoutPlan workoutPlan) {
        this.name = name;
        this.group = group;
        this.mode = mode;
        this.workoutPlan = workoutPlan;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public WorkoutPlan getWorkoutPlan() {
        return workoutPlan;
    }

    public void setWorkoutPlan(WorkoutPlan workoutPlan) {
        this.workoutPlan = workoutPlan;
    }
}
