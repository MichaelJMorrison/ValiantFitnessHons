package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import honours.application.valiantfitness.exercisecategory.WorkoutPlan;
import honours.application.valiantfitness.recyclerviewadapters.WorkoutPreviewRecycler;


public class WorkoutPlanFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "ExerciseFragment";
    public static final String ARG_PLAN = "workout";

    private WorkoutPlan workoutPlan;

    public WorkoutPlanFragment() {
        // Required empty public constructor
    }


    public static WorkoutPlanFragment newInstance(WorkoutPlan workoutPlan) {
        WorkoutPlanFragment fragment = new WorkoutPlanFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAN, workoutPlan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            this.workoutPlan = args.getParcelable(ARG_PLAN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textTitle = view.findViewById(R.id.txt_WPP_Title);
        textTitle.setText(this.workoutPlan.getName());

        WorkoutPreviewRecycler workoutPreviewRecycler = new WorkoutPreviewRecycler(getContext(),this.workoutPlan.getExercises());

        RecyclerView recyclerView = view.findViewById(R.id.rv_Workout_Preview);

        recyclerView.setAdapter(workoutPreviewRecycler);

        LinearLayoutManager layoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Button button = view.findViewById(R.id.btnWorkoutStart);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnWorkoutStart) {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            Bundle bundle = new Bundle();
            bundle.putParcelable(WorkoutExerciseFragment.ARG_WORKOUTPLAN, workoutPlan);
            bundle.putParcelable(ExerciseFragment.ARG_EXERCISE, this.workoutPlan.getExercises().get(1));
            WorkoutExerciseFragment exerciseFragment = new WorkoutExerciseFragment();
            exerciseFragment.setArguments(bundle);

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, exerciseFragment).commit();
        }

    }
}