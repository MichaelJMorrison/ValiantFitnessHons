package honours.application.valiantfitness.workoutdata;

import android.content.Context;

import java.util.List;

import honours.application.valiantfitness.exercisedata.ExcerciseDatabase;
import honours.application.valiantfitness.exercisedata.ExerciseDao;
import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;

public class WorkoutExerciseRepository {

    private static final String TAG = "ExerciseRepository";

    private WorkoutExercisesDao workoutExercisesDao;

    public WorkoutExerciseRepository(Context context){

        super();
        workoutExercisesDao = WorkoutDatabase.getDatabase(context).workoutExercisesDao();

    }

    public void AddWorkoutSet(WorkoutExercises exerciseData) {
        this.workoutExercisesDao.insert(exerciseData);
    }

    public void DeleteExerciseSet(WorkoutExercises exerciseData) {
        this.workoutExercisesDao.delete(exerciseData);
    }

    public List<WorkoutExercises> GetAllExerciseSetDataFromExerciseID(long id) {
        return this.workoutExercisesDao.getAllExerciseSetDataFromWorkoutID(id);
    }

    public List<WorkoutExercises> getAllExerciseSetData() {
        return this.workoutExercisesDao.getAllExerciseSetData();
    }

}
