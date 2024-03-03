package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;
import honours.application.valiantfitness.recyclerviewadapters.ExercisePageAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabExerciseSet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabExerciseSet extends Fragment {

    private Exercise exercise;
    private RecyclerView exerciseRecycler;
    private ExercisePageAdapter RVAdapter;
    private List<ExerciseSetData> exercisesCompleted;
    public static final String ARG_EXERCISE = "exercise";

    public TabExerciseSet() {
        // Required empty public constructor
    }


    public static TabExerciseSet newInstance(Exercise exercise) {
        TabExerciseSet fragment = new TabExerciseSet();
        Bundle args = new Bundle();
        args.putParcelable(ARG_EXERCISE, exercise);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            this.exercise = args.getParcelable(ARG_EXERCISE);
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
    }
}