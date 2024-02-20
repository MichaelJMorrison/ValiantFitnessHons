package honours.application.valiantfitness.recyclerviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisecategory.ExerciseCategory;

public class ExerciseIndividualRecyclerAdapter extends RecyclerView.Adapter<ExerciseIndividualRecyclerAdapter.ExerciseViewHolder> {
    private Context context;

    private List<Exercise> exercises;
    private static final String TAG = "ExerciseIndividualRecyclerAdapter";

    private String mode;
    public ExerciseIndividualRecyclerAdapter(Context context, List<Exercise> exercises) {
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
    public ExerciseIndividualRecyclerAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.individualexerciseitem, parent, false);


        ExerciseIndividualRecyclerAdapter.ExerciseViewHolder stockViewHolder = new ExerciseIndividualRecyclerAdapter.ExerciseViewHolder(itemView, this);
        return stockViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseIndividualRecyclerAdapter.ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        Button btnExercise = holder.itemView.findViewById(R.id.btnExerciseItem);
        btnExercise.setText(exercise.getName());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }



    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private View stockItemView;
        private ExerciseIndividualRecyclerAdapter adapter;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseIndividualRecyclerAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
        }}
}
