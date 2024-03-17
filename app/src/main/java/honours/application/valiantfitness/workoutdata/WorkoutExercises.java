package honours.application.valiantfitness.workoutdata;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "WorkoutExercises")
public class WorkoutExercises {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    public long ID;

    public String name;

    public String Mode;
    public long WorkoutID;

    public WorkoutExercises(long ID) {
        this.ID = ID;
    }


    public WorkoutExercises(){
        this.name = "";
    };

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public long getWorkoutID() {
        return WorkoutID;
    }

    public void setWorkoutID(long workoutID) {
        WorkoutID = workoutID;
    }
}
