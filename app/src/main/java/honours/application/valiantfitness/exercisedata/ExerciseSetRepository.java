package honours.application.valiantfitness.exercisedata;

import android.content.Context;

public class ExerciseSetRepository {
    private static final String TAG = "ExerciseSetRepository";

    private ExerciseSetDao exerciseSetDao;

    public ExerciseSetRepository(Context context){

        super();
        exerciseSetDao = ExcerciseDatabase.getDatabase(context).exerciseSetDao();

    }

    public void AddExerciseSet(ExerciseSetData exerciseData) {
        this.exerciseSetDao.insert(exerciseData);
    }

    public void DeleteExerciseSet(ExerciseSetData exerciseData) {
        this.exerciseSetDao.delete(exerciseData);
    }
}
