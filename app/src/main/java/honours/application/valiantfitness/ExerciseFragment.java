package honours.application.valiantfitness;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

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
public class ExerciseFragment extends Fragment implements TabLayout.OnTabSelectedListener {

  private Exercise exercise;


  private Context context;
   //private ExerciseRepository exerciseRepository;
   private static final String TAG = "ExerciseFragment";

  private RecyclerView exerciseRecycler;
  private ExercisePageAdapter RVAdapter;
  private List<ExerciseSetData> exercisesCompleted;


    public static final String ARG_EXERCISE = "exercise";
    public static final String ARG_CONTEXT = "context";
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
        this.context = getContext();
        TextView textTitle = view.findViewById(R.id.txtExerciseTitle);
        textTitle.setText(this.exercise.getName());

        this.exerciseRecycler = view.findViewById(R.id.rv_ExerciseSet);
        RVAdapter = new ExercisePageAdapter(getContext(),this.exercisesCompleted);
        this.exerciseRecycler.setAdapter(RVAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        exerciseRecycler.setLayoutManager(layoutManager);



        TabLayout tabLayout = view.findViewById(R.id.tbExercise);

        tabLayout.setOnTabSelectedListener(this);
        replaceFragment(new TabExerciseSet());

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
    switch (tab.getPosition()) {
        case 0:
            replaceFragment(new TabExerciseSet());
           // btnLog.setVisibility(View.VISIBLE);
        break;

        case 1:
            replaceFragment(new TabExerciseHistory());
        //  btnLog.setVisibility(View.GONE);
        break;
    }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }



    private void replaceFragment(Fragment fragment) {
        AppCompatActivity activity = (AppCompatActivity) getContext();

        Bundle bundle = new Bundle();

        bundle.putParcelable(ExerciseFragment.ARG_EXERCISE, exercise);
       // bundle.putParcelable(ExerciseFragment.ARG_CONTEXT, (Parcelable) context);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.tabExerciseDisplay,fragment);
        fragmentTransaction.commit();
    }
}