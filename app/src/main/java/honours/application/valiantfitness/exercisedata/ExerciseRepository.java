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

    public long AddExercise(ExerciseData exerciseData) {
    long id = this.exerciseDao.insert(exerciseData);

       return id;

    }


    public List<ExerciseData> GetExercisesWithName(String name) {
        return this.exerciseDao.getExercisesFromName(name);
    }

    public void DeleteExercise(ExerciseData exerciseData) {
        this.exerciseDao.delete(exerciseData);
    }




}
