package honours.application.valiantfitness.recyclerviewadapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import honours.application.valiantfitness.ExerciseFragment;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.WorkoutPlanFragment;
import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisecategory.ExerciseCategory;

public class ExerciseReyclerAdapter extends RecyclerView.Adapter<ExerciseReyclerAdapter.ExerciseViewHolder> {
    private Context context;
    private List<ExerciseCategory> exerciseCategories;

    private List<Exercise> exercises;
    private static final String TAG = "ExerciseReyclerAdapter";

    private View rootview;

    private String mode;
    public ExerciseReyclerAdapter(Context context, List<ExerciseCategory> exerciseCategories, @NonNull View rootview, List<Exercise> exercises) {
        super();
        this.context = context;
        this.exerciseCategories = exerciseCategories;
        this.rootview = rootview;
        this.exercises = exercises;
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


        ExerciseViewHolder exerciseViewHolder = new ExerciseViewHolder(itemView, this);
        return exerciseViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseCategory exerciseCategory = exerciseCategories.get(position);
        TextView textView = holder.itemView.findViewById(R.id.categoryText);
        textView.setText(exerciseCategory.getName());
        holder.exerciseCategory = exerciseCategory;
    }

    @Override
    public int getItemCount() {
        return exerciseCategories.size();
    }



    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View exerciseItemView;
        private View rootView;
        private Context context;
        private ExerciseReyclerAdapter adapter;

        private ExerciseCategory exerciseCategory;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseReyclerAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.exerciseItemView = itemView;
            this.context = this.adapter.context;
            //Listener
            itemView.findViewById(R.id.btnExerciseCategory).setOnClickListener(this);
        }

    public void onClick(View view) {
        AppCompatActivity activity = (AppCompatActivity)this.adapter.context;
        if (this.exerciseCategory.getMode() != null) {
            if(this.exerciseCategory.getMode() == "Exercise") {
                RecyclerView exerciseRecycler = this.adapter.rootview.findViewById(R.id.rv_Exercise);
                RecyclerView exerciseRecycler2 = this.adapter.rootview.findViewById(R.id.rv_Exercise2);
                RecyclerView exerciseRecycler3 = this.adapter.rootview.findViewById(R.id.rv_Exercise3);
                List<Exercise> filteredExercises = FilterExercises(this.exerciseCategory);
                ExerciseIndividualRecyclerAdapter RVAdapter3 = new ExerciseIndividualRecyclerAdapter(this.context, filteredExercises);
               exerciseRecycler3.setAdapter(RVAdapter3);
                TextView txtByBody = this.adapter.rootview.findViewById(R.id.txtBodyType);
                TextView txtByEquipment = this.adapter.rootview.findViewById(R.id.txtByEquipment);
                exerciseRecycler.setVisibility(View.GONE);
                exerciseRecycler2.setVisibility(View.GONE);
                exerciseRecycler3.setVisibility(View.VISIBLE);
                txtByBody.setVisibility(View.GONE);
                txtByEquipment.setVisibility(View.GONE);
            } else if (this.exerciseCategory.getMode() == "Workout") {
                if (this.exerciseCategory != null) {
                    Bundle bundle = new Bundle();

                   bundle.putParcelable(WorkoutPlanFragment.ARG_PLAN, this.exerciseCategory.getWorkoutPlan());
                    WorkoutPlanFragment workoutPlanFragment = new WorkoutPlanFragment();
                    workoutPlanFragment.setArguments(bundle);

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, workoutPlanFragment).commit();
                }
            }

        }

    }


    public List<Exercise> FilterExercises(ExerciseCategory exerciseCategory) {
        List<Exercise> exercises = new ArrayList<>();

        for (Exercise exercise : this.adapter.exercises) {
           if (exercise.getMode() == exerciseCategory.getEquipmentType() && exercise.getGroup() == exerciseCategory.getGroup()) {
               exercises.add(exercise);
           }

        }


        return exercises;
    }
    }

}
