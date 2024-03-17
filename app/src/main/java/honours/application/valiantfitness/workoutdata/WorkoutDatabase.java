package honours.application.valiantfitness.workoutdata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import honours.application.valiantfitness.exercisedata.Converters;
import honours.application.valiantfitness.exercisedata.ExcerciseDatabase;
import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;

@Database(entities = {WorkoutData.class,WorkoutExercises.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class WorkoutDatabase extends RoomDatabase {

    public abstract WorkoutDao workoutDao();

    public abstract WorkoutExercisesDao workoutExercisesDao();
    private static WorkoutDatabase INSTANCE;

    public static WorkoutDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ExcerciseDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    WorkoutDatabase.class, "Workout_Database")
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
