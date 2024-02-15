package honours.application.valiantfitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import honours.application.valiantfitness.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

ActivityMainBinding binding;

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
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_Layout,fragment);
        fragmentTransaction.commit();
    }

}