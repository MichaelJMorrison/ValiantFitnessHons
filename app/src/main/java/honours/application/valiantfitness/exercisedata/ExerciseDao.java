package honours.application.valiantfitness.exercisedata;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ExerciseDao {


    @Delete
    public void delete(ExerciseData exerciseData);

    @Update
    public void update(ExerciseData exerciseData);

    @Query("Select * from ExerciseData")
    public List<ExerciseData> getAllExerciseData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(ExerciseData exerciseData);





}
