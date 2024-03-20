package honours.application.valiantfitness.userdata;

import android.content.Context;

import java.util.List;

import honours.application.valiantfitness.exercisedata.ExcerciseDatabase;
import honours.application.valiantfitness.exercisedata.ExerciseDao;
import honours.application.valiantfitness.exercisedata.ExerciseData;

public class UserRepository {

    private static final String TAG = "UserRepository";

    private UserDao userDao;

    public UserRepository(Context context){

        super();
        userDao = UserDatabase.getDatabase(context).userDao();

    }

    public void AddUser(User user) {
        this.userDao.insert(user);
    }

    public void UpdateUser(User user){
        this.userDao.update(user);
    }


    public User GetUserWithName(String name) {
        return this.userDao.getUserFromName(name);
    }

    public User GetUserFromDeviceID(String name) {
        return this.userDao.getUserFromDeviceID(name);
    }

    public void DeleteExercise(User user) {
        this.userDao.delete(user);
    }




}
