package honours.application.valiantfitness.exercisecategory;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WorkoutPlan implements Parcelable {
       String name;
       List<Exercise> exercises;

       int progress;
       String group;

       public WorkoutPlan(String name, List<Exercise> exercises, String group) {
              this.name = name;
              this.exercises = exercises;
              this.group = group;
              this.progress = 0;
       }

       protected WorkoutPlan(Parcel in) {
              name = in.readString();
              exercises = in.createTypedArrayList(Exercise.CREATOR);
              group = in.readString();
              progress = in.readInt();
       }

       public static final Creator<WorkoutPlan> CREATOR = new Creator<WorkoutPlan>() {
              @Override
              public WorkoutPlan createFromParcel(Parcel in) {
                     return new WorkoutPlan(in);
              }

              @Override
              public WorkoutPlan[] newArray(int size) {
                     return new WorkoutPlan[size];
              }
       };

       @Override
       public int describeContents() {
              return 0;
       }

       @Override
       public void writeToParcel(@NonNull Parcel parcel, int i) {
//https://stackoverflow.com/questions/6300608/how-to-pass-a-parcelable-object-that-contains-a-list-of-objects Credit for better understanding to parse object list
              parcel.writeString(getName());
              parcel.writeList(getExercises());
              parcel.writeInt(getProgress());
       }

       public String getName() {
              return name;
       }

       public void setName(String name) {
              this.name = name;
       }

       public List<Exercise> getExercises() {
              return exercises;
       }

       public void setExercises(List<Exercise> exercises) {
              this.exercises = exercises;
       }

       public String getGroup() {
              return group;
       }

       public void setGroup(String group) {
              this.group = group;
       }

       public int getProgress() {
              return progress;
       }

       public void setProgress(int progress) {
              this.progress = progress;
       }

       public void incrementProgress() {
              this.progress += 1;
       }
}
