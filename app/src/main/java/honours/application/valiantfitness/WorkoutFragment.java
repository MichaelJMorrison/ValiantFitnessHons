package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
import honours.application.valiantfitness.recyclerviewadapters.ExerciseIndividualRecyclerAdapter;
import honours.application.valiantfitness.recyclerviewadapters.ExerciseReyclerAdapter;

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

    private String mode;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutFragment() {
        // Required empty public constructor
        this.mode = "Exercise";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        ECL.add(new ExerciseCategory("Arms","Arms","Exercise","Body"));
        ECL.add(new ExerciseCategory("Shoulders","Shoulders","Exercise","Body"));
        ECL.add(new ExerciseCategory("Chest","Chest","Exercise","Body"));
        ECL.add(new ExerciseCategory("Legs","Legs","Exercise","Body"));
        ECL.add(new ExerciseCategory("Back","Back","Exercise","Body"));
        ECL.add(new ExerciseCategory("Abs","Abs","Exercise","Body"));
    return  ECL;
    };

    public List<ExerciseCategory> getExerciseBody2(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();
        ECL.add(new ExerciseCategory("Arms","Arms","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Shoulders","Shoulders","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Chest","Chest","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Legs","Legs","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Back","Back","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Abs","Abs","Exercise","Equipment"));
        return  ECL;
    };

    public List<ExerciseCategory> getExerciseEquipment(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();

        ECL.add(new ExerciseCategory("Dumbbells","Chest","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Curved Barbell","Chest","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Smith Machine","Chest","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("Kettle Bell","Chest","Exercise","Equipment"));
        ECL.add(new ExerciseCategory("No Equipment","Chest","Exercise","Equipment"));
        return  ECL;
    };

    public List<ExerciseCategory> getWorkoutBody(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();
        ECL.add(new ExerciseCategory("Abs Basic","Abs","Workout"));

        return  ECL;
    };

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

    public List<ExerciseCategory> getWorkoutEquipment(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();

        ECL.add(new ExerciseCategory("Upper Body Rush","Chest","Workout"));
        ECL.add(new ExerciseCategory("Insanity Run","Cardio","Workout",new WorkoutPlan("Insanity Run",getAllExercises(),"Cardio")));
        return  ECL;
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.exerciseCategoryList = getExerciseBody2();
        this.exerciseCategoryList2 = getExerciseBody();
        exerciseRecyler = view.findViewById(R.id.rv_Exercise);
        exerciseRecyler2 = view.findViewById(R.id.rv_Exercise2);
        exerciseRecyler3 = view.findViewById(R.id.rv_Exercise3);

        RVAdapter = new ExerciseReyclerAdapter(getContext(),exerciseCategoryList2,view,getAllExercises());
        RVAdapter2 = new ExerciseReyclerAdapter(getContext(),exerciseCategoryList,view,getAllExercises());

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