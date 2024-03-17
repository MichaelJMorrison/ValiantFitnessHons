package honours.application.valiantfitness.workoutdata;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import honours.application.valiantfitness.exercisecategory.Exercise;
@Entity(tableName = "WorkoutData")
public class WorkoutData {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public long ID;
    String name;
    public String deviceID;
    String group;

    public WorkoutData(long ID) {
        this.ID = ID;
    }
    public WorkoutData() {

    }


    public WorkoutData(String name) {


        this.name = name;

    }

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

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
