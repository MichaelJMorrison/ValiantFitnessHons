package honours.application.valiantfitness;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisecategory.WorkoutPlan;
import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseRepository;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;
import honours.application.valiantfitness.exercisedata.ExerciseSetRepository;
import honours.application.valiantfitness.recyclerviewadapters.ExercisePageAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutExerciseFragment extends Fragment implements View.OnClickListener{

    private Exercise exercise;

    private Context context;

    private WorkoutPlan workoutPlan;


    String android_id;
    private RecyclerView exerciseRecycler;
    private ExercisePageAdapter RVAdapter;
    private List<ExerciseSetData> exercisesCompleted;

    private static final String TAG = "WorkoutExerciseFragment";

    public static final String ARG_WORKOUTPLAN = "workoutplan";

    public WorkoutExerciseFragment() {
        // Required empty public constructor
    }


    public static WorkoutExerciseFragment newInstance(WorkoutPlan workoutPlan) {
        WorkoutExerciseFragment fragment = new WorkoutExerciseFragment();
        Bundle args = new Bundle();
      args.putParcelable(ARG_WORKOUTPLAN, workoutPlan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (getArguments() != null) {
            Bundle args = getArguments();
            this.workoutPlan = args.getParcelable(ARG_WORKOUTPLAN);
            this.exercise = workoutPlan.getExercises().get(workoutPlan.getProgress());
            this.exercisesCompleted = new ArrayList<>();
            this.exercisesCompleted.add(new ExerciseSetData());
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();
        TextView textTitle = view.findViewById(R.id.txtWorkoutExerciseTitle);
        textTitle.setText(this.exercise.getName());

        this.exerciseRecycler = view.findViewById(R.id.rv_WorkoutExerciseSet);
        RVAdapter = new ExercisePageAdapter(getContext(),this.exercisesCompleted);
        this.exerciseRecycler.setAdapter(RVAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        exerciseRecycler.setLayoutManager(layoutManager);

        Button btnLog = view.findViewById(R.id.WorkoutbtnLog);
        btnLog.setOnClickListener(this);
        Button btnSkip = view.findViewById(R.id.WorkoutBtnSkip);
        btnSkip.setOnClickListener(this);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_exercise, container, false);
    }

    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        switch (view.getId()) {



            case R.id.WorkoutbtnLog:
                //Time to access Recycler Data
                //  Log.d(TAG, exercisesCompleted.toString());
                if (exercisesCompleted.size() >= 0) {
                    ExerciseData exerciseData = new ExerciseData(exercise.getName());
                    exerciseData.setDeviceID(android_id);
                    exerciseData.setDate(new Date());
                    //  Log.d(TAG, exerciseData.toString());
                    try {
                        ExerciseRepository exerciseRepository = new ExerciseRepository(getContext());
                        ExerciseSetRepository exerciseSetRepository = new ExerciseSetRepository(getContext());


                        long id = exerciseRepository.AddExercise(exerciseData); //(not activating till I add history section)
                        exerciseData.setID(id);
                        Log.d(TAG, exerciseData.toString());
                        for (ExerciseSetData exerciseSetData:exercisesCompleted) {
                            exerciseSetData.setExerciseID(id);
                            Log.d(TAG, exerciseSetData.toString());
                            exerciseSetRepository.AddExerciseSet(exerciseSetData);
                        }
                    }catch (Error e) {
                        Log.d(TAG, "LOGGING FAILED, ERROR ");
                        e.printStackTrace();
                    }finally {
                        Log.d(TAG, "LOGGING SUCCESSFUL ");


                        if (workoutPlan.getProgress() != workoutPlan.getExercises().size()-1) {
                            workoutPlan.incrementProgress();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(WorkoutExerciseFragment.ARG_WORKOUTPLAN, workoutPlan);

                            Fragment fragment = new WorkoutExerciseFragment();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_Layout,fragment);
                            fragmentTransaction.commit();
                        }else{
                            Fragment fragment = new WorkoutFragment();
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_Layout,fragment);
                            fragmentTransaction.commit();
                        }


                    }
                }


                break;
            case R.id.WorkoutBtnSkip:
                if (workoutPlan.getProgress() != workoutPlan.getExercises().size()-1) {
                    workoutPlan.incrementProgress();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(WorkoutExerciseFragment.ARG_WORKOUTPLAN, workoutPlan);

                    Fragment fragment = new WorkoutExerciseFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_Layout,fragment);
                    fragmentTransaction.commit();
                }else{
                    Fragment fragment = new WorkoutFragment();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_Layout,fragment);
                    fragmentTransaction.commit();
                }
                break;
            default:
                break;

        }
    }
}