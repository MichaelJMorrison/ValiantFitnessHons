package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.recyclerviewadapters.ExerciseSelectorRecycler;
import honours.application.valiantfitness.recyclerviewadapters.WorkoutExerciseRecyler;


public class WorkoutPlanCreatorFragment extends Fragment {


    private String name;
    private String Group;
    private List<Exercise> exerciseList;

    private int position;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;

    public WorkoutPlanCreatorFragment() {
        // Required empty public constructor
    }

    public List<Exercise> getAllExercises(){

        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Bench Press","Chest","Equipment"));
        exercises.add(new Exercise("Curved Barbell Curl","Arms","Equipment"));
        exercises.add(new Exercise("Dumbbell Curl","Arms","Equipment"));
        exercises.add(new Exercise("Dumbbell Press","Chest","Equipment"));
        exercises.add(new Exercise("Barbell Curl","Arms","Equipment"));
        exercises.add(new Exercise("Lateral Raises","Shoulders","Equipment"));
        exercises.add(new Exercise("Lateral Pulldown","Back","Equipment"));
        exercises.add(new Exercise("Weighted Squat","Legs","Equipment"));
        exercises.add(new Exercise("Calve Raises","Legs","Equipment"));
        exercises.add(new Exercise("Unweighted Squat","Legs","Body"));
        exercises.add(new Exercise("Press ups","Chest","Body"));
        exercises.add(new Exercise("Weighted Bulgarian Legs","Legs","Equipment"));
        exercises.add(new Exercise("Unweighted Bulgarian Legs","Legs","Body"));
        exercises.add(new Exercise("Sit Ups","Abs","Body"));
        exercises.add(new Exercise("Plank","Abs","Body"));
        exercises.add(new Exercise("Shoulder Press","Shoulders","Equipment"));
        return exercises;
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
       this.exerciseList.add(new Exercise());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WorkoutExerciseRecyler workoutPreviewRecycler = new WorkoutExerciseRecyler(getContext(),this.exerciseList,view,this);

        recyclerView = view.findViewById(R.id.rv_WorkoutSelector);

        recyclerView.setAdapter(workoutPreviewRecycler);

        LinearLayoutManager layoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ExerciseSelectorRecycler exerciseSelectorRecycler = new ExerciseSelectorRecycler(getContext(),getAllExercises());

        recyclerView2 = view.findViewById(R.id.rv_ExerciseSelector);
        recyclerView2.setAdapter(exerciseSelectorRecycler);

        LinearLayoutManager layoutManager2  = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

       recyclerView2.setLayoutManager(layoutManager2);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_plan_creator, container, false);
    }
}