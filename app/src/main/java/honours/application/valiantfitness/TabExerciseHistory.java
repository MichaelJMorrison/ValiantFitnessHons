package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import honours.application.valiantfitness.exercisecategory.Exercise;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabExerciseHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabExerciseHistory extends Fragment {
    private Exercise exercise;
    public static final String ARG_EXERCISE = "exercise";
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
        return inflater.inflate(R.layout.fragment_tab_exercise_history, container, false);
    }
}