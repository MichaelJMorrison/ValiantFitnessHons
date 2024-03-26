package honours.application.valiantfitness.badge;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Badge")
public class Badge {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String description;
    private String icon;
    private String subIcon;


    public Badge(String title, String description, String icon, String subIcon) {
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.subIcon = subIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubIcon() {
        return subIcon;
    }

    public void setSubIcon(String subIcon) {
        this.subIcon = subIcon;
    }
}

