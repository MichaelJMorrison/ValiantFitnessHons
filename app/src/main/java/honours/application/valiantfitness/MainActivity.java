package honours.application.valiantfitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import honours.application.valiantfitness.databinding.ActivityMainBinding;
import honours.application.valiantfitness.trackerdata.TrackerData;
import honours.application.valiantfitness.trackerdata.TrackerRepository;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

private static final String TAG = "MainActivity";
ActivityMainBinding binding;
private SensorManager sensorManager;
private Sensor sensor;
//https://www.youtube.com/watch?v=l3yBm96qQuI
//https://developer.android.com/develop/sensors-and-location/sensors/sensors_motion#sensors-motion-stepcounter
// https://developer.android.com/health-and-fitness/guides/basic-fitness-app/read-step-count-data#analyze-data
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            long StepCount = (long) sensorEvent.values[0];
       //   Log.d(TAG, "Step Counter:" + Long.toString(StepCount));
            TrackerRepository trackerRepository = new TrackerRepository(getBaseContext());

            Date date = new Date();
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            }catch (ParseException | Error error) {
                Log.d(TAG,error.toString());
            }

            try {
                TrackerData trackerData = trackerRepository.GetDataFromDateMode("Steps",date);
                if(trackerData == null){
                    trackerData = new TrackerData();
                    trackerData.setDataName("Steps");
                    trackerData.setDate(date);
                    trackerData.setValue((double) StepCount);
                    trackerRepository.AddTrackedData(trackerData);
                }else{
                    trackerData.setValue((double) StepCount);
                    trackerRepository.UpdateTrackedData(trackerData);
                }


            }catch (Error error){
                Log.d(TAG,error.toString());
            }finally {
                Log.d(TAG,"Step Counter Updated");
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
protected  void onStop(){
    super.onStop();
    if (sensor != null){
        sensorManager.unregisterListener((SensorEventListener) this);
    }
}
    @Override
    protected void onResume() {
        super.onResume();

        if (sensor != null){
          sensorManager.registerListener((SensorEventListener) this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new WorkoutFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {




        switch(item.getItemId()) {

            case R.id.Workout:
                replaceFragment(new WorkoutFragment());
                break;
            case R.id.Tracker:
                replaceFragment(new TrackerFragment());
                break;
            case R.id.Social:
                replaceFragment(new SocialFragment());
                break;
            case R.id.Profile:
                replaceFragment(new ProfileFragment());
                break;
        }


            return true;
        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG,"NO PERMISSION");
          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, Manifest.permission.ACTIVITY_RECOGNITION.hashCode());

        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (sensor == null){
            Log.d(TAG,"No sensor found on this device");


        }




    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_Layout,fragment);
        fragmentTransaction.commit();
    }

//https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/ CREDIT
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Manifest.permission.ACTIVITY_RECOGNITION.hashCode()){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "Permission Granted");
            }
        }
    }
}