package honours.application.valiantfitness.trackerdata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;



@Dao
public interface TrackerDao {

    @Delete
    public void delete(TrackerData user);

    @Update
    public void update(TrackerData user);

    @Query("Select * from TrackerData")
    public List<TrackerData> getAllTrackerData();

    @Query("Select * from TrackerData WHERE dataName like :name")
    public List<TrackerData> getDataFromSection(String name);

    @Query("Select * from TrackerData WHERE dataName like :name AND date =:date")
    public TrackerData getDataFromDateMode(String name,Long date);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(TrackerData trackerData);

}
