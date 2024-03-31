package honours.application.valiantfitness.workoutdata;

import android.content.Context;

import java.util.List;

import honours.application.valiantfitness.exercisedata.ExcerciseDatabase;


public class WorkoutRepository {

    private static final String TAG = "WorkoutRepository";

    private WorkoutDao workoutDao;

    public WorkoutRepository(Context context){

        super();
        workoutDao = WorkoutDatabase.getDatabase(context).workoutDao();

    }

    public long AddWorkout(WorkoutData workoutData) {
        long id = this.workoutDao.insert(workoutData);

        return id;

    }


    public List<WorkoutData> GetWorkoutFromName(String name) {
        return this.workoutDao.getWorkoutFromName(name);
    }

    public List<WorkoutData> GetWorkoutFromDeviceID(String deviceID) {
        return this.workoutDao.getWorkoutFromDeviceID(deviceID);
    }

    public void DeleteWorkout(WorkoutData exerciseData) {
        this.workoutDao.delete(exerciseData);
    }

    public void WipeData(){this.workoutDao.WipeData();}

}
