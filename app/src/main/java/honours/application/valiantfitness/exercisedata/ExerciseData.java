package honours.application.valiantfitness.exercisedata;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "ExerciseData")
public class ExerciseData {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public long ID;
    public String deviceID;

    public Date date;

    public String name;


   // public List<ExerciseSetData> setDataList;

    public ExerciseData(long ID) {
        this.ID = ID;
    }
    public ExerciseData() {

    }


    public ExerciseData(String name) {


        this.name = name;

    }
@NonNull
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ExerciseData{" +
                "ID=" + ID +
                ", deviceID='" + deviceID + '\'' +
                ", date=" + date +
                ", name='" + name + '\'' +
                '}';
    }
}
