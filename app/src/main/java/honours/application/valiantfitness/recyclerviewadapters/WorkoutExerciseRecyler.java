package honours.application.valiantfitness.recyclerviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.WorkoutPlanCreatorFragment;
import honours.application.valiantfitness.exercisecategory.Exercise;

public class WorkoutExerciseRecyler  extends RecyclerView.Adapter<WorkoutExerciseRecyler.ExerciseViewHolder> {

    private Context context;

    private View rootview;

    private List<Exercise> exercises;
    private static final String TAG = "WorkoutExerciseReycler";

    private WorkoutPlanCreatorFragment fragment;


    public WorkoutExerciseRecyler(Context context, List<Exercise> exercises, @NonNull View view, WorkoutPlanCreatorFragment fragment) {
        this.fragment = fragment;
        this.context = context;
        this.exercises = exercises;
        this.rootview = view;
    }
    @NonNull
    @Override
    public WorkoutExerciseRecyler.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.workoutexerciseitem, parent, false);


        WorkoutExerciseRecyler.ExerciseViewHolder exerciseViewHolder = new WorkoutExerciseRecyler.ExerciseViewHolder(itemView, this);
        return exerciseViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutExerciseRecyler.ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }



    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private View exerciseViewItem;
        private WorkoutExerciseRecyler adapter;

        public ExerciseViewHolder(@NonNull View itemView, WorkoutExerciseRecyler adapter) {
            super(itemView);
            this.adapter = adapter;
            this.exerciseViewItem = itemView;
            itemView.findViewById(R.id.btnExerciseSelector).setOnClickListener(this);
        }

        public void onClick(View view) {
            RecyclerView recyclerView= this.adapter.rootview.findViewById(R.id.rv_WorkoutSelector);
            RecyclerView recyclerView2= this.adapter.rootview.findViewById(R.id.rv_ExerciseSelector);
            Button buttonSave= this.adapter.rootview.findViewById(R.id.btnWorkoutSaveWorkout);
            Button buttonCancel= this.adapter.rootview.findViewById(R.id.btnWorkoutCancel);
            fragment.setPosition(this.getAdapterPosition());
            buttonSave.setVisibility(View.GONE);
            buttonCancel.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            recyclerView2.setVisibility(View.VISIBLE);
        }

    }
}
