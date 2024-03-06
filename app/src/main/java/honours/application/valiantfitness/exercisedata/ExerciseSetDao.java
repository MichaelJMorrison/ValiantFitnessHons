package honours.application.valiantfitness.exercisedata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciseSetDao {

    @Delete
    public void delete(ExerciseSetData exerciseData);

    @Update
    public void update(ExerciseSetData exerciseData);

    @Query("Select * from ExerciseSetData")
    public List<ExerciseSetData> getAllExerciseSetData();

    @Query("Select * from ExerciseSetData WHERE ExerciseID like:id")
    public List<ExerciseSetData> getAllExerciseSetDataFromExerciseID(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ExerciseSetData exerciseData);


}
