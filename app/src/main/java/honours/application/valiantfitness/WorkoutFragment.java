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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import honours.application.valiantfitness.databinding.ActivityMainBinding;
import honours.application.valiantfitness.exercisecategory.ExerciseCategory;
import honours.application.valiantfitness.recyclerviewadapters.ExerciseReyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private RecyclerView exerciseRecyler;
    private List<ExerciseCategory> exerciseCategoryList;
    private ExerciseReyclerAdapter RVAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutFragment() {
        // Required empty public constructor
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.exerciseCategoryList = new ArrayList<>();
        this.exerciseCategoryList.add(new ExerciseCategory("Arms","Arms"));
        this.exerciseCategoryList.add(new ExerciseCategory("Legs","Legs"));
        this.exerciseCategoryList.add(new ExerciseCategory("Shoulders","Shoulders"));
        this.exerciseCategoryList.add(new ExerciseCategory("Dumbbells","Arms"));
        this.exerciseCategoryList.add(new ExerciseCategory("Bench","Legs"));
        this.exerciseCategoryList.add(new ExerciseCategory("Smith Machine","Shoulders"));
        this.exerciseCategoryList.add(new ExerciseCategory("Row","Arms"));
        this.exerciseCategoryList.add(new ExerciseCategory("Back","Back"));
        this.exerciseCategoryList.add(new ExerciseCategory("Shoulders","Shoulders"));
        exerciseRecyler = view.findViewById(R.id.rv_Exercise);
        RVAdapter = new ExerciseReyclerAdapter(getContext(),exerciseCategoryList);
        exerciseRecyler.setAdapter(RVAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        exerciseRecyler.setLayoutManager(layoutManager);

    }
}