package honours.application.valiantfitness.recyclerviewadapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.exercisecategory.ExerciseCategory;

public class ExerciseReyclerAdapter extends RecyclerView.Adapter<ExerciseReyclerAdapter.ExerciseViewHolder> {
    private Context context;
    private List<ExerciseCategory> exerciseCategories;
    private static final String TAG = "ExerciseReyclerAdapter";

    private View rootview;

    private String mode;
    public ExerciseReyclerAdapter(Context context, List<ExerciseCategory> exerciseCategories, @NonNull View rootview) {
        super();
        this.context = context;
        this.exerciseCategories = exerciseCategories;
        this.rootview = rootview;
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

            //Listener
            itemView.findViewById(R.id.btnExerciseCategory).setOnClickListener(this);
        }

    public void onClick(View view) {

        if (this.exerciseCategory.getMode() != null) {
            if(this.exerciseCategory.getMode() == "Exercise") {
                RecyclerView exerciseRecycler = this.adapter.rootview.findViewById(R.id.rv_Exercise);
                RecyclerView exerciseRecycler2 = this.adapter.rootview.findViewById(R.id.rv_Exercise2);
                RecyclerView exerciseRecycler3 = this.adapter.rootview.findViewById(R.id.rv_Exercise3);

                TextView txtByBody = this.adapter.rootview.findViewById(R.id.txtBodyType);
                TextView txtByEquipment = this.adapter.rootview.findViewById(R.id.txtByEquipment);
                exerciseRecycler.setVisibility(View.GONE);
                exerciseRecycler2.setVisibility(View.GONE);
                exerciseRecycler3.setVisibility(View.VISIBLE);
                txtByBody.setVisibility(View.GONE);
                txtByEquipment.setVisibility(View.GONE);
            }
        }

    }

    }

}
