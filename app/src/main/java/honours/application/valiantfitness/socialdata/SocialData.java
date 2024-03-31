package honours.application.valiantfitness.socialdata;

import android.os.Parcel;
import android.os.Parcelable;


import androidx.annotation.NonNull;

import honours.application.valiantfitness.exercisecategory.Exercise;

public class SocialData implements Parcelable {

   private String profile;

   private String objective;

   private String description;

   private String location;

   private int mode;

    public SocialData(String profile, String objective, String description, String location) {
        this.profile = profile;
        this.objective = objective;
        this.description = description;
        this.location = location;
    }

    public SocialData(String profile, String objective, String description, String location, int mode) {
        this.profile = profile;
        this.objective = objective;
        this.description = description;
        this.location = location;
        this.mode = mode;
    }

    public SocialData() {
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(getProfile());
        parcel.writeString(getObjective());
        parcel.writeString(getDescription());
        parcel.writeString(getLocation());
        parcel.writeInt(getMode());
    }

    public static final Creator<SocialData> CREATOR = new Creator<SocialData>() {
        @Override
        public SocialData createFromParcel(Parcel in) {
            SocialData socialData = new SocialData();


            socialData.setProfile(in.readString());
            socialData.setObjective(in.readString());
            socialData.setDescription(in.readString());
            socialData.setLocation(in.readString());
            socialData.setMode(in.readInt());

            return socialData;
        }

        @Override
        public SocialData[] newArray(int i) {
            return new SocialData[0];
        }

    };
}
