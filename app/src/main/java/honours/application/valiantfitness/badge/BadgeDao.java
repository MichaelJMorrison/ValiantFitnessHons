package honours.application.valiantfitness.badge;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import honours.application.valiantfitness.userdata.User;

@Dao
public interface BadgeDao {
    @Delete
    public void delete(Badge badge);

    @Update
    public void update(Badge badge);

    @Query("Select * from Badge")
    public List<Badge> getAllBadges();

    @Query("Select * from Badge WHERE title like :title")
    public Badge getBadgeFromTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Badge badge);

    @Query("DELETE FROM Badge")
    public void WipeData();

}
