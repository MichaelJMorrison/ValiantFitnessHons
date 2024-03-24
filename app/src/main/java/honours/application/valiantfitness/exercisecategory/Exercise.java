package honours.application.valiantfitness.exercisecategory;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {

    String name;

    int repetitionCompleted;
    int weight;
    String description;

    String Group;
    String Mode;

    String Image;

    public Exercise(String name, String group, String mode) {
        this.name = name;
        this.Group = group;
        this.Mode = mode;
    }

    public Exercise(String name, String group, String mode, String description) {
        this.name = name;
        this.Group = group;
        this.Mode = mode;
        this.description = description;
    }

    public Exercise(){
        this.name = "";
    };

    protected Exercise(Parcel in) {

        name = in.readString();

    }
    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            Exercise exercise = new Exercise();


            exercise.setName(in.readString());
            exercise.setDescription(in.readString());

            return  exercise;
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getRepetitionCompleted() {
        return repetitionCompleted;
    }

    public void setRepetitionCompleted(int repetitionCompleted) {
        this.repetitionCompleted = repetitionCompleted;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //  out.writeLong(getId());
        parcel.writeString(getName());
        parcel.writeString(getDescription());

    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", repetitionCompleted=" + repetitionCompleted +
                ", weight=" + weight +
                ", description='" + description + '\'' +
                ", Group='" + Group + '\'' +
                ", Mode='" + Mode + '\'' +
                '}';
    }
}
