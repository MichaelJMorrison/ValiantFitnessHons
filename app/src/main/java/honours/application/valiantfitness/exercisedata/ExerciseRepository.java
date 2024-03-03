package honours.application.valiantfitness.exercisedata;

import android.content.Context;

import java.util.List;

public class ExerciseRepository {

    private static final String TAG = "ExerciseRepository";

    private ExerciseDao exerciseDao;

    public ExerciseRepository(Context context){

        super();
        exerciseDao = ExcerciseDatabase.getDatabase(context).exerciseDao();

   }

    public void AddExercise(ExerciseData exerciseData) {
        this.exerciseDao.insert(exerciseData);
    }

    public void DeleteExercise(ExerciseData exerciseData) {
        this.exerciseDao.delete(exerciseData);
    }




}
