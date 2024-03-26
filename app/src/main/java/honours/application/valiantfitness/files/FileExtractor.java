package honours.application.valiantfitness.files;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisecategory.WorkoutPlan;

public class FileExtractor {

    private View view;

    private static final String TAG = "FileExtractor";



    public FileExtractor(View view) {
        this.view = view;

    }


    public List<Exercise> extractExerciseFile() {
        List<Exercise> exercises = new ArrayList<>();
        try {
            String line;

            //https://www.youtube.com/watch?app=desktop&v=HxwcIFt-_5E Credits to figuring out how to get this working
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            InputStream file = activity.getBaseContext().getAssets().open("exercise.csv");

            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                String[] details = scanner.nextLine().split(",");
                Exercise exercise = new Exercise(details[0],details[1],details[2],details[3] );
                exercise.setImage(details[4]);
                exercise.setGuide(details[5]);
              //  Log.d(TAG, exercise.toString());
                exercises.add(exercise);
            }

            file.close();

        } catch (Error error) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Log.d(TAG, exercises.toString());
        return exercises;
    }

    public List<WorkoutPlan> extractWorkoutFile() {
        List<WorkoutPlan> workoutPlans = new ArrayList<>();
        List<Exercise> exercises = extractExerciseFile();
        try {
            String line;

            //https://www.youtube.com/watch?app=desktop&v=HxwcIFt-_5E Credits to figuring out how to get this working
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            InputStream file = activity.getBaseContext().getAssets().open("workout.csv");

            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                List<Exercise> exerciseFilterd = new ArrayList<>();
                String[] details = scanner.nextLine().split(",");

                for (int i = 3; i < details.length-1; i++) {
                    for (Exercise exercise:exercises
                         ) {
                        Log.d("FileExtractor","E " + exercise.getName());
                        Log.d("FileExtractor","D " + details[i].toString());
                        if (details[i].toString().equals(exercise.getName().toString())){
                            exerciseFilterd.add(exercise);
                        }

                    }
                }

                WorkoutPlan workout = new WorkoutPlan(details[0],details[1],details[2] ,exerciseFilterd);

                workoutPlans.add(workout);
            }

            file.close();

        } catch (Error error) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Log.d(TAG, workoutPlans.toString());
        return workoutPlans;
    }




}

