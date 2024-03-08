package honours.application.valiantfitness.recyclerviewadapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import honours.application.valiantfitness.ExerciseFragment;
import honours.application.valiantfitness.R;
import honours.application.valiantfitness.exercisecategory.Exercise;

public class WorkoutPreviewRecycler extends RecyclerView.Adapter<WorkoutPreviewRecycler.ExerciseViewHolder> {
    private Context context;

    private List<Exercise> exercises;
    private static final String TAG = "WorkoutPreviewRecycler";

    private String mode;
    public WorkoutPreviewRecycler(Context context, List<Exercise> exercises) {
        super();
        this.context = context;
        this.exercises = exercises;

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
    public WorkoutPreviewRecycler.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.individualexerciseitem, parent, false);


        WorkoutPreviewRecycler.ExerciseViewHolder stockViewHolder = new WorkoutPreviewRecycler.ExerciseViewHolder(itemView, this);
        return stockViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutPreviewRecycler.ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        Button btnExercise = holder.itemView.findViewById(R.id.btnExerciseItem);
        btnExercise.setText(exercise.getName());
        btnExercise.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }



    class ExerciseViewHolder extends RecyclerView.ViewHolder  {

        private View exerciseViewItem;
        private WorkoutPreviewRecycler adapter;

        public ExerciseViewHolder(@NonNull View itemView, WorkoutPreviewRecycler adapter) {
            super(itemView);
            this.adapter = adapter;
            this.exerciseViewItem = itemView;

        }


    }
}
