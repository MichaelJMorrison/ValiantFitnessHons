package honours.application.valiantfitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import honours.application.valiantfitness.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

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
            Log.d("MainActivity", Long.toString(StepCount));
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


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (sensor == null){



        }



    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_Layout,fragment);
        fragmentTransaction.commit();
    }

}