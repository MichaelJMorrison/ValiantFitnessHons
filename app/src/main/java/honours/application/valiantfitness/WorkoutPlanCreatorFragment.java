package honours.application.valiantfitness;

import android.app.AlertDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisedata.ExerciseRepository;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;
import honours.application.valiantfitness.exercisedata.ExerciseSetRepository;
import honours.application.valiantfitness.files.FileExtractor;
import honours.application.valiantfitness.recyclerviewadapters.ExerciseSelectorRecycler;
import honours.application.valiantfitness.recyclerviewadapters.WorkoutExerciseRecyler;
import honours.application.valiantfitness.workoutdata.WorkoutData;
import honours.application.valiantfitness.workoutdata.WorkoutExerciseRepository;
import honours.application.valiantfitness.workoutdata.WorkoutExercises;
import honours.application.valiantfitness.workoutdata.WorkoutRepository;


public class WorkoutPlanCreatorFragment extends Fragment implements View.OnClickListener {


    private String name;
    private String Group;
    private List<Exercise> exerciseList;

    private static final String TAG = "WorkoutPlanCreatorFragment";
    private int position;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;

    private Button buttonSave;
    private Button buttonCancel;

    private EditText txtWorkoutInputName;
    private String DeviceID;

    private RadioGroup rbGroupWorkout;


    public WorkoutPlanCreatorFragment() {
        // Required empty public constructor
    }

    public List<Exercise> getAllExercises(View view){



            FileExtractor fileExtractor = new FileExtractor(view);



            return fileExtractor.extractExerciseFile();



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
       this.DeviceID = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WorkoutExerciseRecyler workoutPreviewRecycler = new WorkoutExerciseRecyler(getContext(),this.exerciseList,view,this);

        recyclerView = view.findViewById(R.id.rv_WorkoutSelector);

        recyclerView.setAdapter(workoutPreviewRecycler);

        LinearLayoutManager layoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ExerciseSelectorRecycler exerciseSelectorRecycler = new ExerciseSelectorRecycler(getContext(),getAllExercises(view),workoutPreviewRecycler);

        recyclerView2 = view.findViewById(R.id.rv_ExerciseSelector);
        recyclerView2.setAdapter(exerciseSelectorRecycler);

        LinearLayoutManager layoutManager2  = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

       recyclerView2.setLayoutManager(layoutManager2);

       txtWorkoutInputName= view.findViewById(R.id.txtWorkoutInputName);
        rbGroupWorkout= view.findViewById(R.id.rbGroupWorkout);


       buttonSave= view.findViewById(R.id.btnWorkoutSaveWorkout);
       buttonCancel= view.findViewById(R.id.btnWorkoutCancel);
        buttonSave.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnWorkoutCancel) {
            buttonSave.setVisibility(View.VISIBLE);
            buttonCancel.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.GONE);
        }
        if (view.getId() == R.id.btnWorkoutSaveWorkout) {


            if (ValidationPass() == true){
                Log.d(TAG,"VALIDATION SUCCESS");

                try{
                    WorkoutRepository workoutRepository = new WorkoutRepository(getContext());
                    WorkoutExerciseRepository workoutExerciseRepository = new WorkoutExerciseRepository(getContext());


                    WorkoutData workoutData = new WorkoutData(txtWorkoutInputName.getText().toString());

                    RadioButton radioButton = rbGroupWorkout.findViewById(rbGroupWorkout.getCheckedRadioButtonId());

                    workoutData.setGroup(radioButton.getText().toString());

                    workoutData.setDeviceID(this.DeviceID);

                    Log.d(TAG,radioButton.getText().toString());
                    long id = workoutRepository.AddWorkout(workoutData);

                    for (Exercise exercise:exerciseList) {
                        WorkoutExercises workoutExercise = new WorkoutExercises();
                        workoutExercise.setWorkoutID(id);
                        workoutExercise.setName(exercise.getName());
                        workoutExercise.setMode(exercise.getMode());

                        Log.d(TAG, workoutExercise.toString());
                        workoutExerciseRepository.AddWorkoutSet(workoutExercise);
                    }

                }catch (Error error) {
                    Log.d(TAG, "LOGGING FAILED, ERROR ");
                    error.printStackTrace();
                }finally {
                    Log.d(TAG,"WORKOUT CREATED");
                    //https://developer.android.com/develop/ui/views/components/dialogs#AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Workout plan has successfully been created!");
                    builder.setTitle("Workout Plan");
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
            }else{
                Log.d(TAG,"VALIDATION FAILURE");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Please make sure a name, type and all exercise items have an exercise selected before proceeding");
                builder.setTitle("Workout plan failed to create");
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    public boolean ValidationPass(){

       if(this.exerciseList.size()<0){
           return false;
       }

       if(txtWorkoutInputName.getText().length()==0) {
           return false;
       }

       if (rbGroupWorkout.getCheckedRadioButtonId() == -1) {
           return  false;
       }

        for (Exercise exercise:this.exerciseList) {
            if (exercise.getName() =="") {
                return false;
            }
        }

       return true;
    };



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