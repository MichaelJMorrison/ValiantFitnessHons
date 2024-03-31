package honours.application.valiantfitness.workoutdata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import honours.application.valiantfitness.exercisedata.ExerciseData;

@Dao
public interface WorkoutDao {

    @Delete
    public void delete(WorkoutData workoutData);

    @Update
    public void update(WorkoutData workoutData);

    @Query("Select * from WorkoutData")
    public List<WorkoutData> getAllWorkoutData();

    @Query("Select * from WorkoutData WHERE name like :name")
    public List<WorkoutData> getWorkoutFromName(String name);

    @Query("Select * from WorkoutData WHERE deviceID like :deviceID")
    public List<WorkoutData> getWorkoutFromDeviceID(String deviceID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(WorkoutData workoutData);

    @Query("DELETE FROM WorkoutData")
    public void WipeData();

}
