package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisecategory.ExerciseCategory;
import honours.application.valiantfitness.exercisecategory.WorkoutPlan;
import honours.application.valiantfitness.exercisedata.ExerciseRepository;
import honours.application.valiantfitness.files.FileExtractor;
import honours.application.valiantfitness.recyclerviewadapters.ExerciseIndividualRecyclerAdapter;
import honours.application.valiantfitness.recyclerviewadapters.ExerciseReyclerAdapter;
import honours.application.valiantfitness.workoutdata.WorkoutData;
import honours.application.valiantfitness.workoutdata.WorkoutExerciseRepository;
import honours.application.valiantfitness.workoutdata.WorkoutExercises;
import honours.application.valiantfitness.workoutdata.WorkoutRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private RecyclerView exerciseRecyler;
    private RecyclerView exerciseRecyler2;

    private TextView txtByEquipment;
    private TextView txtByBody;
    private RecyclerView exerciseRecyler3;
    private List<ExerciseCategory> exerciseCategoryList;
    private List<ExerciseCategory> exerciseCategoryList2;
    private ExerciseReyclerAdapter RVAdapter;
    private ExerciseReyclerAdapter RVAdapter2;
    private ExerciseIndividualRecyclerAdapter RVAdapter3;
    private static final String TAG = "WorkoutFragment";
    String deviceId;

    private String mode;



    public WorkoutFragment() {
        // Required empty public constructor
        this.mode = "Exercise";
    }

       public static WorkoutFragment newInstance() {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.deviceId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    public List<ExerciseCategory> getExerciseBody(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();
        ECL.add(new ExerciseCategory("Arms","Arms","arm","Exercise","Body"));
        ECL.add(new ExerciseCategory("Shoulders","Shoulders","shoulder","Exercise","Body"));
        ECL.add(new ExerciseCategory("Chest","Chest","core","Exercise","Body"));
        ECL.add(new ExerciseCategory("Legs","Legs","leg","Exercise","Body"));
        ECL.add(new ExerciseCategory("Back","Back","back","Exercise","Body"));
        ECL.add(new ExerciseCategory("Abs","Abs","core","Exercise","Body"));
    return  ECL;
    };

    public List<ExerciseCategory> getExerciseBody2(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();
        ECL.add(new ExerciseCategory("Arms","Arms","arm","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Shoulders","Shoulders","shoulder","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Chest","Chest","core","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Legs","Legs","leg","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Back","Back","back","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Abs","Abs","core","Exercise","Equipment"));
        return  ECL;
    };



    public List<ExerciseCategory> getWorkoutBody(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();
        FileExtractor fileExtractor = new FileExtractor(getView());
        List<WorkoutPlan>workoutPlanList = fileExtractor.extractWorkoutFile();

        for (WorkoutPlan workoutPlan:workoutPlanList
             ) {
            if(workoutPlan.getGroup().equals("Body")){
                ECL.add(new ExerciseCategory(workoutPlan.getName(),workoutPlan.getGroup(),workoutPlan.getImage(),"Workout",workoutPlan));
            }
        }


        for (ExerciseCategory exerciseCategory:getCreatedExerciseList("Body")
        ) {
            ECL.add(exerciseCategory);
        }
        return  ECL;
    };


    public List<Exercise> getAllExercisesFile(View view) {


        FileExtractor fileExtractor = new FileExtractor(view);



        return fileExtractor.extractExerciseFile();

    }
    public List<ExerciseCategory> getWorkoutEquipment(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();

        FileExtractor fileExtractor = new FileExtractor(getView());
        List<WorkoutPlan>workoutPlanList = fileExtractor.extractWorkoutFile();

        for (WorkoutPlan workoutPlan:workoutPlanList
        ) {
            if(workoutPlan.getGroup().equals("Equipment")){
                ECL.add(new ExerciseCategory(workoutPlan.getName(),workoutPlan.getGroup(),workoutPlan.getImage(),"Workout",workoutPlan));
            }
        }


        for (ExerciseCategory exerciseCategory:getCreatedExerciseList("Equipment")
        ) {
            ECL.add(exerciseCategory);
        }


            return  ECL;
    };

    public List<ExerciseCategory> getCreatedExerciseList(String group) {

        List<ExerciseCategory> exerciseCategories = new ArrayList<>();

        WorkoutRepository workoutRepository = new WorkoutRepository(getContext());

        WorkoutExerciseRepository workoutExerciseRepository = new WorkoutExerciseRepository(getContext());
        List<WorkoutData> workoutData = workoutRepository.GetWorkoutFromDeviceID(deviceId);
        Log.d(TAG,workoutData.toString());
        for (WorkoutData workoutDataExtract:workoutData
        ) {
            Log.d(TAG,workoutDataExtract.getGroup());
            if (workoutDataExtract.getGroup().toString().equals(group)) {
                List<WorkoutExercises> workoutExercises = workoutExerciseRepository.GetAllExerciseSetDataFromExerciseID(workoutDataExtract.getID());

                List<Exercise> exercises = new ArrayList<>();

                for (WorkoutExercises workoutExercise:workoutExercises
                ) {
                    Exercise exercise = new Exercise();
                    exercise.setName(workoutExercise.getName());
                    exercise.setMode(workoutExercise.getMode());
                    exercises.add(exercise);
                }

                WorkoutPlan workoutPlan = new WorkoutPlan(workoutDataExtract.getName(),exercises,workoutDataExtract.getGroup());
                exerciseCategories.add(new ExerciseCategory(workoutPlan.getName(),workoutPlan.getGroup(),"Workout",workoutPlan));
            }

        }

        return exerciseCategories;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.exerciseCategoryList = getExerciseBody2();
        this.exerciseCategoryList2 = getExerciseBody();
        exerciseRecyler = view.findViewById(R.id.rv_Exercise);
        exerciseRecyler2 = view.findViewById(R.id.rv_Exercise2);
        exerciseRecyler3 = view.findViewById(R.id.rv_Exercise3);

        List<Exercise> exerciseList = getAllExercisesFile(view);

        RVAdapter = new ExerciseReyclerAdapter(getContext(),exerciseCategoryList2,view,exerciseList);
        RVAdapter2 = new ExerciseReyclerAdapter(getContext(),exerciseCategoryList,view,exerciseList);

        exerciseRecyler.setAdapter(RVAdapter);
        exerciseRecyler2.setAdapter(RVAdapter2);
       // exerciseRecyler3.setAdapter(RVAdapter3);
        txtByBody = view.findViewById(R.id.txtBodyType);
        txtByEquipment = view.findViewById(R.id.txtByEquipment);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
       exerciseRecyler.setLayoutManager(layoutManager);
       exerciseRecyler2.setLayoutManager(layoutManager2);
        exerciseRecyler3.setLayoutManager(layoutManager3);
        //swapRecyclers(view);
        Button btnWorkout = view.findViewById(R.id.btnWorkout);
        btnWorkout.setOnClickListener(this);
        Button btnCreate = view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
        Button btnPlan = view.findViewById(R.id.btnPlan);
        btnPlan.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnWorkout:
                this.mode = "Exercise";
                showViewer(view);
                swapRecyclers(view);
                break;
            case R.id.btnCreate:
                AppCompatActivity activity = (AppCompatActivity) getContext();



                WorkoutPlanCreatorFragment workoutPlanCreatorFragment = new WorkoutPlanCreatorFragment();


                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, workoutPlanCreatorFragment).commit();

                break;
            case R.id.btnPlan:
                this.mode = "Plan";
                showViewer(view);
                swapRecyclers(view);
                break;

            default:
                break;

        }



    }

    public void hideViewer(View view) {
    exerciseRecyler.setVisibility(View.GONE);
    exerciseRecyler2.setVisibility(View.GONE);
    exerciseRecyler3.setVisibility(View.VISIBLE);
    txtByBody.setVisibility(View.GONE);
    txtByEquipment.setVisibility(View.GONE);

    }

    public void showViewer(View view) {
        exerciseRecyler.setVisibility(View.VISIBLE);
        exerciseRecyler2.setVisibility(View.VISIBLE);
        exerciseRecyler3.setVisibility(View.GONE);
        txtByEquipment.setVisibility(View.VISIBLE);
        txtByBody.setVisibility(View.VISIBLE);
    }
    public void swapRecyclers(View view){
        if (this.mode == "Exercise"){
            this.exerciseCategoryList = getExerciseBody2();
            this.exerciseCategoryList2 = getExerciseBody();

        }else {
            this.exerciseCategoryList = getWorkoutEquipment();
            this.exerciseCategoryList2 = getWorkoutBody();
        }

        RVAdapter.setExerciseCategories(exerciseCategoryList2);
        RVAdapter2.setExerciseCategories(exerciseCategoryList);

    }

}