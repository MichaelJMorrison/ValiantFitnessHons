package honours.application.valiantfitness.userdata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import honours.application.valiantfitness.exercisedata.Converters;
import honours.application.valiantfitness.exercisedata.ExcerciseDatabase;
import honours.application.valiantfitness.exercisedata.ExerciseDao;
import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseSetDao;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;

@Database(entities = {User.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class UserDatabase  extends RoomDatabase {

    public abstract UserDao userDao();


    private static UserDatabase INSTANCE;

    public static UserDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (UserDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UserDatabase.class, "User_Database")
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
