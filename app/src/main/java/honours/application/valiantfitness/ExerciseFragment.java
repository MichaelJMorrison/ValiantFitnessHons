package honours.application.valiantfitness;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import honours.application.valiantfitness.exercisecategory.*;
import honours.application.valiantfitness.exercisedata.*;
import honours.application.valiantfitness.recyclerviewadapters.*;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment implements View.OnClickListener {

  private Exercise exercise;
   //private ExerciseRepository exerciseRepository;
   private static final String TAG = "ExerciseFragment";
  private ExercisePageAdapter exercisePageAdapter;
  private RecyclerView exerciseRecycler;
  private ExercisePageAdapter RVAdapter;
  private List<ExerciseSetData> exercisesCompleted;

    public static final String ARG_EXERCISE = "exercise";
    public ExerciseFragment() {
        // Required empty public constructor
    }


    public static ExerciseFragment newInstance(Exercise exercise) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
       args.putParcelable(ARG_EXERCISE, exercise);
       fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // this.exerciseRepository = new ExerciseRepository(getContext());
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
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textTitle = view.findViewById(R.id.txtExerciseTitle);
        textTitle.setText(this.exercise.getName());

        this.exerciseRecycler = view.findViewById(R.id.rv_ExerciseSet);
        RVAdapter = new ExercisePageAdapter(getContext(),this.exercisesCompleted);
        this.exerciseRecycler.setAdapter(RVAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        exerciseRecycler.setLayoutManager(layoutManager);

        Button btnLog = view.findViewById(R.id.btnLog);
        btnLog.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnLog:
                //Time to access Recycler Data
                Log.d(TAG, exercisesCompleted.toString());
                if (exercisesCompleted.size() >= 0) {
                    ExerciseData exerciseData = new ExerciseData(exercise.getName());

                    try {
                     //   exerciseRepository.AddExercise(exerciseData); (not activating till I add history section)
                    }catch (Error e) {
                        Log.d(TAG, "LOGGING FAILED, ERROR ");
                        e.printStackTrace();
                    }finally {
                        Log.d(TAG, "LOGGING SUCCESSFUL ");

                    }
                }


              break;
            default:
                break;

        }
    }
}