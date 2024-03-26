package honours.application.valiantfitness.socialdata;

import android.provider.ContactsContract;

public class SocialData {

   private ContactsContract.Profile profile;

   private String objective;

   private String description;

   private String location;

    public SocialData(ContactsContract.Profile profile, String objective, String description, String location) {
        this.profile = profile;
        this.objective = objective;
        this.description = description;
        this.location = location;
    }

    public ContactsContract.Profile getProfile() {
        return profile;
    }

    public void setProfile(ContactsContract.Profile profile) {
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
}
