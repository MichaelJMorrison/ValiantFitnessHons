package honours.application.valiantfitness.recyclerviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.exercisecategory.Exercise;

public class ExerciseSelectorRecycler extends RecyclerView.Adapter<ExerciseSelectorRecycler.ExerciseViewHolder> {
    private Context context;

    private List<Exercise> exercises;
    private static final String TAG = "ExerciseSelectorRecycler";

    private WorkoutExerciseRecyler workoutExerciseRecyler;

    private String mode;
    public ExerciseSelectorRecycler(Context context, List<Exercise> exercises) {
        super();
        this.context = context;
        this.exercises = exercises;

    }

    public ExerciseSelectorRecycler(Context context, List<Exercise> exercises, WorkoutExerciseRecyler workoutExerciseRecyler) {
        super();
        this.context = context;
        this.exercises = exercises;
        this.workoutExerciseRecyler = workoutExerciseRecyler;
    }



    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    @NonNull
    @Override
    public ExerciseSelectorRecycler.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.individualexerciseitem, parent, false);


        ExerciseSelectorRecycler.ExerciseViewHolder stockViewHolder = new ExerciseSelectorRecycler.ExerciseViewHolder(itemView, this);
        return stockViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseSelectorRecycler.ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        Button btnExercise = holder.itemView.findViewById(R.id.btnExerciseItem);
        btnExercise.setText(exercise.getName());

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }



    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener   {

        private View exerciseViewItem;
        private ExerciseSelectorRecycler adapter;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseSelectorRecycler adapter) {
            super(itemView);
            this.adapter = adapter;
            this.exerciseViewItem = itemView;
            itemView.findViewById(R.id.btnExerciseItem).setOnClickListener(this);
        }
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            if (view.getId() == R.id.btnExerciseItem) {
            this.adapter.workoutExerciseRecyler.updateExercise(exercises.get(getAdapterPosition()));
                RecyclerView recyclerView= this.adapter.workoutExerciseRecyler.getRootview().findViewById(R.id.rv_WorkoutSelector);
                RecyclerView recyclerView2= this.adapter.workoutExerciseRecyler.getRootview().findViewById(R.id.rv_ExerciseSelector);
                Button buttonSave= this.adapter.workoutExerciseRecyler.getRootview().findViewById(R.id.btnWorkoutSaveWorkout);
                Button buttonCancel= this.adapter.workoutExerciseRecyler.getRootview().findViewById(R.id.btnWorkoutCancel);

                buttonSave.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.GONE);
            }
        }

    }
}
