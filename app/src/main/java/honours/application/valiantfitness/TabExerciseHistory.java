package honours.application.valiantfitness;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseRepository;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;
import honours.application.valiantfitness.exercisedata.ExerciseSetRepository;
import honours.application.valiantfitness.recyclerviewadapters.ExerciseHistoryAdapter;
import honours.application.valiantfitness.recyclerviewadapters.ExercisePageAdapter;
import honours.application.valiantfitness.recyclerviewadapters.ExerciseReyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabExerciseHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabExerciseHistory extends Fragment {
    private Exercise exercise;

    public static final String ARG_EXERCISE = "exercise";

    private Context context;

    String android_id;
    private static final String TAG = "TabExerciseHistory";
    private Button btnLog;

    private RecyclerView exerciseRecycler;
    private ExerciseHistoryAdapter RVAdapter;

    private List<ExerciseData> exerciseHistory;
    public TabExerciseHistory() {
        // Required empty public constructor
    }


    public static TabExerciseHistory newInstance(Exercise exercise) {
        TabExerciseHistory fragment = new TabExerciseHistory();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_exercise_history, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ExerciseRepository exerciseRepository = new ExerciseRepository(getContext());
        this.exerciseHistory = exerciseRepository.GetExercisesWithName(this.exercise.getName());
        ExerciseSetRepository exerciseRepository2 = new ExerciseSetRepository(getContext());
        List<ExerciseSetData> setData = exerciseRepository2.getAllExerciseSetData();
        Log.d(TAG, exercise.toString());
        Log.d(TAG, setData.toString());

 exerciseRecycler = view.findViewById(R.id.rv_exercise_history);
      RVAdapter = new ExerciseHistoryAdapter(getContext(),this.exerciseHistory);
       exerciseRecycler.setAdapter(RVAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        exerciseRecycler.setLayoutManager(layoutManager);
    }
}