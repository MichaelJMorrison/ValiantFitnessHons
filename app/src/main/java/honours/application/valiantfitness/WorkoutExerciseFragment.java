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

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import honours.application.valiantfitness.badge.Badge;
import honours.application.valiantfitness.badge.BadgeRepository;
import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisecategory.WorkoutPlan;
import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseRepository;
import honours.application.valiantfitness.exercisedata.ExerciseSetComparator;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;
import honours.application.valiantfitness.exercisedata.ExerciseSetRepository;
import honours.application.valiantfitness.recyclerviewadapters.ExercisePageAdapter;
import honours.application.valiantfitness.workoutdata.WorkoutRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutExerciseFragment extends Fragment implements View.OnClickListener {

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
        RVAdapter = new ExercisePageAdapter(getContext(), this.exercisesCompleted);
        this.exerciseRecycler.setAdapter(RVAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
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
                       ProgressLog();
                    }

                });


                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.WorkoutBtnSkip:
                if (workoutPlan.getProgress() != workoutPlan.getExercises().size() - 1) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Are you sure you wish to skip this exercise?");
                    builder1.setTitle(this.exercise.getName());

                    builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            workoutPlan.incrementProgress();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(WorkoutExerciseFragment.ARG_WORKOUTPLAN, workoutPlan);
                            Fragment fragment = new WorkoutExerciseFragment();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_Layout, fragment);
                            fragmentTransaction.commit();
                        }

                    });


                    AlertDialog dialog1 = builder1.create();
                    dialog1.show();
                } else {

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                    builder2.setMessage("Are you sure you wish to complete this exercise?");
                    builder2.setTitle(this.exercise.getName());

                    builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Fragment fragment = new WorkoutFragment();
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_Layout, fragment);
                            fragmentTransaction.commit();
                        }

                    });


                    AlertDialog dialog2 = builder2.create();
                    dialog2.show();


                }
                break;
            default:
                break;

        }
    }

    public void ProgressLog(){
        if (exercisesCompleted.size() >= 0) {
            ExerciseData exerciseData = new ExerciseData(exercise.getName());
            exerciseData.setDeviceID(android_id);
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                exerciseData.setDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
            }catch (ParseException error) {

            }
            //exerciseData.setDate(new Date());
            //  Log.d(TAG, exerciseData.toString());
            try {
                ExerciseRepository exerciseRepository = new ExerciseRepository(getContext());
                ExerciseSetRepository exerciseSetRepository = new ExerciseSetRepository(getContext());


                long id = exerciseRepository.AddExercise(exerciseData); //(not activating till I add history section)
                exerciseData.setID(id);
                Log.d(TAG, exerciseData.toString());
                for (ExerciseSetData exerciseSetData : exercisesCompleted) {
                    exerciseSetData.setExerciseID(id);
                    Log.d(TAG, exerciseSetData.toString());
                    exerciseSetRepository.AddExerciseSet(exerciseSetData);
                }
            } catch (Error e) {
                Log.d(TAG, "LOGGING FAILED, ERROR ");
                e.printStackTrace();
            } finally {
                Log.d(TAG, "LOGGING SUCCESSFUL ");

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Workout Exercise Saved!");
                builder.setTitle(this.exercise.getName());
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (workoutPlan.getProgress() != workoutPlan.getExercises().size() - 1) {
                            workoutPlan.incrementProgress();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(WorkoutExerciseFragment.ARG_WORKOUTPLAN, workoutPlan);

                            Fragment fragment = new WorkoutExerciseFragment();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_Layout, fragment);
                            fragmentTransaction.commit();
                        } else {
                            BadgeAward();
                            Fragment fragment = new WorkoutFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_Layout, fragment);
                            fragmentTransaction.commit();
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();





            }
        }
    }

    public void BadgeAward(){
        BadgeRepository badgeRepository = new BadgeRepository(getContext());
        Badge badge = badgeRepository.GetBadgeFromTitle("Workout Journey");

        if (workoutPlan.getProgress() == workoutPlan.getExercises().size() - 1 & badge == null){
            badge = new Badge("Workout Journey", "You have completed your first workout plan!","bronze","trainingicon");
            badgeRepository.AddBadge(badge);
            Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: Workout Journey!", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        if (badgeRepository.GetBadgeFromTitle("Beginner Gym Logger") == null){
            badge = new Badge("Beginner Gym Logger", "You have logged your first exercise!","bronze","squaticon");
            badgeRepository.AddBadge(badge);
            Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: " + badge.getTitle(), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        ExerciseRepository exerciseRepository = new ExerciseRepository(getContext());
        List<ExerciseData> exerciseData = exerciseRepository.GetAllExercises();

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
            badge = new Badge("Heavy Lifter", "You have lifted more than 100KG for the first time!","gold","squaticon");
            badgeRepository.AddBadge(badge);
            Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: " + badge.getTitle(), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }



    }

}