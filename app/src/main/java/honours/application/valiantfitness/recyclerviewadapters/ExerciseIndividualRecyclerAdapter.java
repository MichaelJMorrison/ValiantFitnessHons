package honours.application.valiantfitness.recyclerviewadapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import honours.application.valiantfitness.ExerciseFragment;
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
        ImageView imageView = holder.itemView.findViewById(R.id.imgExerciseIcon);

        if (exercise.getImage() != null){
            int drawable = context.getResources().getIdentifier(exercise.getImage(),"drawable",context.getPackageName());

            imageView.setImageResource(drawable);
        }else{
            int drawable = context.getResources().getIdentifier("core","drawable",context.getPackageName());

            imageView.setImageResource(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }



    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View exerciseViewItem;
        private ExerciseIndividualRecyclerAdapter adapter;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseIndividualRecyclerAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.exerciseViewItem = itemView;
            itemView.findViewById(R.id.btnExerciseItem).setOnClickListener(this);
        }

        @Override
        public void onClick (View view) {
            int position = getAdapterPosition();
            Exercise exercise = this.adapter.exercises.get(position);
            AppCompatActivity activity = (AppCompatActivity)this.adapter.context;

            if (view.getId() == R.id.btnExerciseItem) {
                if (exercise != null) {
                    Bundle bundle = new Bundle();

                    bundle.putParcelable(ExerciseFragment.ARG_EXERCISE, exercise);
                    ExerciseFragment exerciseFragment = new ExerciseFragment();
                    exerciseFragment.setArguments(bundle);

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, exerciseFragment).commit();
                }
            }
        }
    }
}
