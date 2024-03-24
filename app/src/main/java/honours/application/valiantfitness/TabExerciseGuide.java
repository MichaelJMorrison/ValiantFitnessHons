package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import honours.application.valiantfitness.exercisecategory.Exercise;

public class TabExerciseGuide extends Fragment {

    public static final String ARG_EXERCISE = "exercise";

    private ImageView gifExeciseImage;

    private Exercise exercise;


    public TabExerciseGuide() {
        // Required empty public constructor
    }


    public static TabExerciseGuide newInstance(Exercise exercise) {
        TabExerciseGuide fragment = new TabExerciseGuide();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gifExeciseImage = view.findViewById(R.id.gifExeciseImage);
        Glide.with(this).load(R.drawable.dog).into(gifExeciseImage);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_exercise_guide, container, false);
    }
}