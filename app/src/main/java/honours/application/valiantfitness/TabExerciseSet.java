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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import honours.application.valiantfitness.badge.Badge;
import honours.application.valiantfitness.badge.BadgeRepository;
import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseRepository;
import honours.application.valiantfitness.exercisedata.ExerciseSetComparator;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;
import honours.application.valiantfitness.exercisedata.ExerciseSetRepository;
import honours.application.valiantfitness.recyclerviewadapters.ExercisePageAdapter;
import honours.application.valiantfitness.trackerdata.TrackerData;
import honours.application.valiantfitness.trackerdata.TrackerRepository;
import honours.application.valiantfitness.workoutdata.WorkoutRepository;

import android.provider.Settings.Secure;

import com.google.android.material.snackbar.Snackbar;

public class TabExerciseSet extends Fragment implements View.OnClickListener{

    private Exercise exercise;
    private RecyclerView exerciseRecycler;
    private ExercisePageAdapter RVAdapter;
    private List<ExerciseSetData> exercisesCompleted;
    public static final String ARG_EXERCISE = "exercise";
    public static final String ARG_CONTEXT = "context";
    private Context context;

    private  TrackerRepository trackerRepository;

    String android_id;
    private static final String TAG = "TabExerciseSet";
    private Button btnLog;
    public TabExerciseSet() {
        // Required empty public constructor
    }

    public TabExerciseSet(Context context) {
        // Required empty public constructor
        this.context = context;
        trackerRepository = new TrackerRepository(context);
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
        trackerRepository = new TrackerRepository(getContext());
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
                        try {
                            LogProgress();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }

                });


                AlertDialog dialog = builder.create();
                dialog.show();


                break;
            default:
                break;

        }
    }

    public void LogProgress() throws ParseException {
        if (exercisesCompleted.size() >= 0) {
            ExerciseData exerciseData = new ExerciseData(exercise.getName());
            exerciseData.setDeviceID(android_id);
            //https://stackoverflow.com/questions/5050170/how-do-i-get-a-date-without-time-in-java
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                exerciseData.setDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
            }catch (ParseException error) {

            }

            //  Log.d(TAG, exerciseData.toString());
            try {
                ExerciseRepository exerciseRepository = new ExerciseRepository(getContext());
                ExerciseSetRepository exerciseSetRepository = new ExerciseSetRepository(getContext());
                TrackerData trackerData = trackerRepository.GetDataFromDateMode("Volume",exerciseData.getDate());
                Double Volume = 0.0;

                long id = exerciseRepository.AddExercise(exerciseData); //(not activating till I add history section)
                exerciseData.setID(id);
                Log.d(TAG, exerciseData.toString());
                for (ExerciseSetData exerciseSetData:exercisesCompleted) {
                    exerciseSetData.setExerciseID(id);
                    Log.d(TAG, exerciseSetData.toString());
                    exerciseSetRepository.AddExerciseSet(exerciseSetData);
                    Volume+= exerciseSetData.getWeight() * (double) exerciseSetData.getRep();
                }
                if (trackerData != null){
                    trackerData.setValue(trackerData.getValue()+Volume);
                    trackerRepository.UpdateTrackedData(trackerData);
                }else{
                    trackerData = new TrackerData();
                    trackerData.setDataName("Volume");
                    trackerData.setValue(Volume);
                    trackerData.setDate(exerciseData.getDate());
                    trackerRepository.AddTrackedData(trackerData);
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
                        BadgeAward();
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

    public void BadgeAward(){
        BadgeRepository badgeRepository = new BadgeRepository(getContext());
        Badge badge = badgeRepository.GetBadgeFromTitle("Beginner Gym Logger");

        if (badge == null){
            badge = new Badge("Beginner Gym Logger", "You have logged your first exercise!","bronze","squaticon");
            badgeRepository.AddBadge(badge);
            Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: First Workout Created!", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        ExerciseRepository exerciseRepository = new ExerciseRepository(getContext());
        List<ExerciseData> exerciseData = exerciseRepository.GetAllExercises();

        Log.d(TAG,Integer.toString(exerciseData.size()));

        if (exerciseData.size()>=10 & badgeRepository.GetBadgeFromTitle("Novice Gym Logger") == null){
            badge = new Badge("Novice Gym Logger", "You have logged more than 10 exercises!","bronze","note");
            badgeRepository.AddBadge(badge);
            Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: " + badge.getTitle(), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        if (exerciseData.size()>=50 & badgeRepository.GetBadgeFromTitle("Intermediate Gym Logger") == null){
            badge = new Badge("Intermediate Gym Logger", "You have logged more than 50 exercises!","silver","note");
            badgeRepository.AddBadge(badge);
            Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: " + badge.getTitle(), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        if (exerciseData.size()>=100 & badgeRepository.GetBadgeFromTitle("Marathon Gym Logger") == null){
            badge = new Badge("Marathon Gym Logger", "You have logged more than 100 exercises!","gold","note");
            badgeRepository.AddBadge(badge);
            Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: " + badge.getTitle(), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }




        Collections.sort(exercisesCompleted,Collections.reverseOrder( new ExerciseSetComparator()));

        if (exercisesCompleted.get(0).getWeight()>=60 & badgeRepository.GetBadgeFromTitle("Novice Lifter") == null){
            badge = new Badge("Novice Lifter", "You have lifted more than 60KG for the first time!","silver","squaticon");
            badgeRepository.AddBadge(badge);
            Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: " + badge.getTitle(), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        if (exercisesCompleted.get(0).getWeight()>=100 & badgeRepository.GetBadgeFromTitle("Heavy Lifter") == null){
            badge = new Badge("Heavy Lifter", "You have lifted more than 100KG for the first time!","silver","squaticon");
            badgeRepository.AddBadge(badge);
            Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: " + badge.getTitle(), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }

}