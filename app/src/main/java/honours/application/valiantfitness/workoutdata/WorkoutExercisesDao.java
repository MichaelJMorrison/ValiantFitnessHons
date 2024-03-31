package honours.application.valiantfitness.workoutdata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import honours.application.valiantfitness.exercisedata.ExerciseSetData;
@Dao
public interface WorkoutExercisesDao {
    @Delete
    public void delete(WorkoutExercises exerciseData);

    @Update
    public void update(WorkoutExercises exerciseData);

    @Query("Select * from WorkoutExercises")
    public List<WorkoutExercises> getAllExerciseSetData();

    @Query("Select * from WorkoutExercises WHERE WorkoutID like:id")
    public List<WorkoutExercises> getAllExerciseSetDataFromWorkoutID(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(WorkoutExercises exerciseData);

    @Query("DELETE FROM WorkoutExercises")
    public void WipeData();
}
