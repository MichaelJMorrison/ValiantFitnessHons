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
import honours.application.valiantfitness.exercisecategory.ExerciseCategory;

public class ExerciseIndividualRecyclerAdapter extends RecyclerView.Adapter<ExerciseIndividualRecyclerAdapter.ExerciseViewHolder> {
    private Context context;
    private List<ExerciseCategory> exerciseCategories;
    private static final String TAG = "ExerciseIndividualRecyclerAdapter";

    private String mode;
    public ExerciseIndividualRecyclerAdapter(Context context, List<ExerciseCategory> exerciseCategories) {
        super();
        this.context = context;
        this.exerciseCategories = exerciseCategories;
    }

    public void setExerciseCategories(List<ExerciseCategory> newExerciseCategories) {
        exerciseCategories = newExerciseCategories;
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
        ExerciseCategory exerciseCategory = exerciseCategories.get(position);
        Button btnExercise = holder.itemView.findViewById(R.id.btnExerciseItem);
        btnExercise.setText(exerciseCategory.getName());
    }

    @Override
    public int getItemCount() {
        return exerciseCategories.size();
    }



    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private View stockItemView;
        private ExerciseIndividualRecyclerAdapter adapter;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseIndividualRecyclerAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
        }}
}
