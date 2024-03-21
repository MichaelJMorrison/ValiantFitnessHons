package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TrackerFragment extends Fragment implements View.OnClickListener{


    private Button WeightButton;

    private Button CaloriesButton;

    private  Button StepsButton;

    public TrackerFragment() {
        // Required empty public constructor
    }

    public static TrackerFragment newInstance(String param1, String param2) {
        TrackerFragment fragment = new TrackerFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.CaloriesButton = view.findViewById(R.id.CaloriesButton);
        this.WeightButton = view.findViewById(R.id.WeightButton);
        this.StepsButton = view.findViewById(R.id.StepsButton);
        this.CaloriesButton.setOnClickListener(this);
        this.WeightButton.setOnClickListener(this);
        this.StepsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        Bundle bundle = new Bundle();

       switch (view.getId()){
           case R.id.CaloriesButton:
               bundle.putString(TrackerTemplateFragment.ARG_MODE,"Calories");
               break;
           case R.id.StepsButton:
               bundle.putString(TrackerTemplateFragment.ARG_MODE,"Steps");
               break;
           case R.id.WeightButton:
               bundle.putString(TrackerTemplateFragment.ARG_MODE,"Weight");
               break;
       }

        TrackerTemplateFragment trackerTemplateFragment = new TrackerTemplateFragment();
        trackerTemplateFragment.setArguments(bundle);

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, trackerTemplateFragment).commit();
    }
}