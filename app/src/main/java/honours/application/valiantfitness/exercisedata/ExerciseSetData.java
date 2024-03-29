package honours.application.valiantfitness.exercisedata;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ExerciseSetData")
public class ExerciseSetData  {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public long ID;


    public long ExerciseID;
    public double weight;
    public int rep;

    public ExerciseSetData(double weight, int rep) {
        this.weight = weight;
        this.rep = rep;
    }



    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public ExerciseSetData() {
        this.rep = 0;
        this.weight = 0;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getExerciseID() {
        return ExerciseID;
    }

    public void setExerciseID(long exerciseID) {
        ExerciseID = exerciseID;
    }

    @Override
    public String toString() {
        return "ExerciseSetData{" +
                "ID=" + ID +
                ", ExerciseID=" + ExerciseID +
                ", weight=" + weight +
                ", rep=" + rep +
                '}';
    }
}
