package honours.application.valiantfitness.recyclerviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.exercisecategory.ExerciseCategory;

public class ExerciseReyclerAdapter extends RecyclerView.Adapter<ExerciseReyclerAdapter.ExerciseViewHolder> {
    private Context context;
    private List<ExerciseCategory> exerciseCategories;
    private static final String TAG = "ExerciseReyclerAdapter";

    private String mode;
    public ExerciseReyclerAdapter(Context context, List<ExerciseCategory> exerciseCategories) {
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
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.exercisecategoryitem, parent, false);


        ExerciseViewHolder stockViewHolder = new ExerciseViewHolder(itemView, this);
        return stockViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseCategory exerciseCategory = exerciseCategories.get(position);
        TextView textView = holder.itemView.findViewById(R.id.categoryText);
        textView.setText(exerciseCategory.getName());
    }

    @Override
    public int getItemCount() {
        return exerciseCategories.size();
    }



    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private View stockItemView;
        private ExerciseReyclerAdapter adapter;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseReyclerAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
        }}

}
