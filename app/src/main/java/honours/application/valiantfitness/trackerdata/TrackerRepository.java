package honours.application.valiantfitness.trackerdata;

import android.content.Context;

import java.util.Date;
import java.util.List;

import honours.application.valiantfitness.userdata.User;
import honours.application.valiantfitness.userdata.UserDao;
import honours.application.valiantfitness.userdata.UserDatabase;

public class TrackerRepository {
    private static final String TAG = "TrackerRepository";

    private TrackerDao trackerDao;

    public TrackerRepository(Context context){

        super();
        trackerDao = TrackerDatabase.getDatabase(context).trackerDao();

    }

    public void AddTrackedData(TrackerData trackerData) {
        this.trackerDao.insert(trackerData);
    }

    public void UpdateTrackedData(TrackerData trackerData){
        this.trackerDao.update(trackerData);
    }




    public List<TrackerData> GetDataFromSection(String name) {
        return this.trackerDao.getDataFromSection(name);
    }

    public void DeleteTrackedData(TrackerData trackerData) {
        this.trackerDao.delete(trackerData);
    }

    public TrackerData GetDataFromDateMode(String dataName, Date date) {

        return this.trackerDao.getDataFromDateMode(dataName,date.getTime());
    }
}
