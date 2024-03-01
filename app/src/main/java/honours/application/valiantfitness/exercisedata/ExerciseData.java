package honours.application.valiantfitness.exercisedata;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity
public class ExerciseData {
    @PrimaryKey
    public int id;

    public Date date;

    public String name;

    public List<ExerciseSetData> setDataList;

    public ExerciseData(int id, Date date, String name, List<ExerciseSetData> setDataList) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.setDataList = setDataList;
    }
}
