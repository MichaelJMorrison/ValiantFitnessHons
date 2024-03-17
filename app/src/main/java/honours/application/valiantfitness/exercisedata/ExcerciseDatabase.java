package honours.application.valiantfitness.exercisedata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import honours.application.valiantfitness.workoutdata.WorkoutDao;
import honours.application.valiantfitness.workoutdata.WorkoutData;

@Database(entities = {ExerciseData.class,ExerciseSetData.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class ExcerciseDatabase extends RoomDatabase {
    public abstract ExerciseDao exerciseDao();
    public abstract ExerciseSetDao exerciseSetDao();

    //public abstract WorkoutDao workoutDao();
    private static ExcerciseDatabase INSTANCE;

    public static ExcerciseDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ExcerciseDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ExcerciseDatabase.class, "Exercise_Database")
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
