package honours.application.valiantfitness.exercisedata;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ExerciseSetData")
public class ExerciseSetData  {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int ID;


    public int ExerciseID;
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

    @Override
    public String toString() {
        return "exerciseSetData{" +
                "weight=" + weight +
                ", rep=" + rep +
                '}';
    }
}
