package honours.application.valiantfitness.badge;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import honours.application.valiantfitness.exercisedata.Converters;



@Database(entities = {Badge.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class BadgeDatabase extends RoomDatabase {

    public abstract BadgeDao badgeDao();

    private static BadgeDatabase INSTANCE;

    public static BadgeDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (BadgeDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    BadgeDatabase.class, "Badge_Database")
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
