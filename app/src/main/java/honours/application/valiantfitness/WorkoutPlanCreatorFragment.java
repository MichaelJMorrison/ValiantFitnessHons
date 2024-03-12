package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import honours.application.valiantfitness.exercisecategory.Exercise;


public class WorkoutPlanCreatorFragment extends Fragment {


    private String name;
    private String Group;
    private List<Exercise> exerciseList;

    private RecyclerView recyclerView;


    public WorkoutPlanCreatorFragment() {
        // Required empty public constructor
    }

    public static WorkoutPlanCreatorFragment newInstance(String param1, String param2) {
        WorkoutPlanCreatorFragment fragment = new WorkoutPlanCreatorFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
       this.exerciseList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_plan_creator, container, false);
    }
}