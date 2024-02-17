package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import honours.application.valiantfitness.databinding.ActivityMainBinding;
import honours.application.valiantfitness.exercisecategory.ExerciseCategory;
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
    private List<ExerciseCategory> exerciseCategoryList;
    private List<ExerciseCategory> exerciseCategoryList2;
    private ExerciseReyclerAdapter RVAdapter;
    private ExerciseReyclerAdapter RVAdapter2;

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
        ECL.add(new ExerciseCategory("Arms","Arms"));
        ECL.add(new ExerciseCategory("Shoulders","Shoulders"));
        ECL.add(new ExerciseCategory("Chest","Chest"));
        ECL.add(new ExerciseCategory("Legs","Legs"));
        ECL.add(new ExerciseCategory("Back","Back"));
        ECL.add(new ExerciseCategory("Abs","Abs"));
    return  ECL;
    };

    public List<ExerciseCategory> getExerciseEquipment(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();

        ECL.add(new ExerciseCategory("Dumbbells","Chest"));
        ECL.add(new ExerciseCategory("Curved Barbell","Chest"));
        ECL.add(new ExerciseCategory("Smith Machine","Chest"));
        ECL.add(new ExerciseCategory("Kettle Bell","Chest"));
        ECL.add(new ExerciseCategory("No Equipment","Chest"));
        return  ECL;
    };

    public List<ExerciseCategory> getWorkoutBody(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();
        ECL.add(new ExerciseCategory("Abs Basic","Abs"));

        return  ECL;
    };

    public List<ExerciseCategory> getWorkoutEquipment(){

        List<ExerciseCategory> ECL;
        ECL= new ArrayList<>();

        ECL.add(new ExerciseCategory("Upper Body Rush","Chest"));
        ECL.add(new ExerciseCategory("Insanity Run","Cardio"));
        return  ECL;
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.exerciseCategoryList = getExerciseEquipment();
        this.exerciseCategoryList2 = getExerciseBody();
        exerciseRecyler = view.findViewById(R.id.rv_Exercise);
        exerciseRecyler2 = view.findViewById(R.id.rv_Exercise2);
        RVAdapter = new ExerciseReyclerAdapter(getContext(),exerciseCategoryList2);
        RVAdapter2 = new ExerciseReyclerAdapter(getContext(),exerciseCategoryList);
        exerciseRecyler.setAdapter(RVAdapter);
        exerciseRecyler2.setAdapter(RVAdapter2);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
       exerciseRecyler.setLayoutManager(layoutManager);
       exerciseRecyler2.setLayoutManager(layoutManager2);

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

                swapRecyclers(view);
                break;
            case R.id.btnCreate:

                break;
            case R.id.btnPlan:
                this.mode = "Plan";
                swapRecyclers(view);
                break;
            default:
                break;

        }



    }

    public void swapRecyclers(View view){
        if (this.mode == "Exercise"){
            this.exerciseCategoryList = getExerciseEquipment();
            this.exerciseCategoryList2 = getExerciseBody();

        }else {
            this.exerciseCategoryList = getWorkoutEquipment();
            this.exerciseCategoryList2 = getWorkoutBody();
        }

        RVAdapter.setExerciseCategories(exerciseCategoryList2);
        RVAdapter2.setExerciseCategories(exerciseCategoryList);

    }

}