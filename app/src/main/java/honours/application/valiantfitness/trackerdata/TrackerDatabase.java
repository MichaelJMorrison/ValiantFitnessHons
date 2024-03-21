package honours.application.valiantfitness.trackerdata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import honours.application.valiantfitness.exercisedata.Converters;
import honours.application.valiantfitness.userdata.User;
import honours.application.valiantfitness.userdata.UserDao;
import honours.application.valiantfitness.userdata.UserDatabase;
@Database(entities = {TrackerData.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class TrackerDatabase extends RoomDatabase {
    public abstract TrackerDao trackerDao();


    private static TrackerDatabase INSTANCE;

    public static TrackerDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (TrackerDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TrackerDatabase.class, "Tracker_Database")
                            .fallbackToDestructiveMigration()
                            // the following is only for testing / initial dev purposes
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
