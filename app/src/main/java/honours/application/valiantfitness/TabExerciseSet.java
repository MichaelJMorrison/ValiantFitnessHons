package honours.application.valiantfitness;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseRepository;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;
import honours.application.valiantfitness.exercisedata.ExerciseSetRepository;
import honours.application.valiantfitness.recyclerviewadapters.ExercisePageAdapter;
import android.provider.Settings.Secure;

public class TabExerciseSet extends Fragment implements View.OnClickListener{

    private Exercise exercise;
    private RecyclerView exerciseRecycler;
    private ExercisePageAdapter RVAdapter;
    private List<ExerciseSetData> exercisesCompleted;
    public static final String ARG_EXERCISE = "exercise";
    public static final String ARG_CONTEXT = "context";
    private Context context;

    String android_id;
    private static final String TAG = "TabExerciseSet";
    private Button btnLog;
    public TabExerciseSet() {
        // Required empty public constructor
    }

    public TabExerciseSet(Context context) {
        // Required empty public constructor
        this.context = context;
    }


    public static TabExerciseSet newInstance(Exercise exercise) {
        TabExerciseSet fragment = new TabExerciseSet();
        Bundle args = new Bundle();
        args.putParcelable(ARG_EXERCISE, exercise);
      //  args.putParcelable(ARG_CONTEXT, (Parcelable) context);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android_id = Secure.getString(getContext().getContentResolver(),Secure.ANDROID_ID);
        if (getArguments() != null) {
            Bundle args = getArguments();
            this.exercise = args.getParcelable(ARG_EXERCISE);
            this.context = args.getParcelable(ARG_CONTEXT);
            this.exercisesCompleted = new ArrayList<>();
            this.exercisesCompleted.add(new ExerciseSetData());
            // this.exercisesCompleted.add(this.exercise);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_exercise_log, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.exerciseRecycler = view.findViewById(R.id.rv_ExerciseSetTab);
        RVAdapter = new ExercisePageAdapter(getContext(),this.exercisesCompleted);
        this.exerciseRecycler.setAdapter(RVAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        exerciseRecycler.setLayoutManager(layoutManager);
        this.btnLog = view.findViewById(R.id.btnLog);
        btnLog.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {



            case R.id.btnLog:
                //Time to access Recycler Data
              //  Log.d(TAG, exercisesCompleted.toString());


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you wish to save this exercise?");
                builder.setTitle(this.exercise.getName());

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    LogProgress();
                    }

                });


                AlertDialog dialog = builder.create();
                dialog.show();


                break;
            default:
                break;

        }
    }

    public void LogProgress(){
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Workout Exercise Saved!");
                builder.setTitle(this.exercise.getName());
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppCompatActivity activity = (AppCompatActivity) getActivity();
                        Fragment fragment = new WorkoutFragment();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_Layout,fragment);
                        fragmentTransaction.commit();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }


}