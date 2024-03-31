package honours.application.valiantfitness.userdata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import honours.application.valiantfitness.exercisedata.ExerciseData;

@Dao
public interface UserDao {

    @Delete
    public void delete(User user);

    @Update
    public void update(User user);

    @Query("Select * from User")
    public List<User> getAllUsers();

    @Query("Select * from User WHERE name like :name")
    public User getUserFromName(String name);

    @Query("Select * from User WHERE DeviceID like :name")
    public User getUserFromDeviceID(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(User user);

    @Query("DELETE FROM User")
    public void WipeData();

}
