package honours.application.valiantfitness.recyclerviewadapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;

public class ExercisePageAdapter extends RecyclerView.Adapter<ExercisePageAdapter.ExerciseViewHolder> {
    private Context context;

    private List<ExerciseSetData> exercises;
    private static final String TAG = "ExcercisePageAdapter";

    private String mode;
    public ExercisePageAdapter(Context context, List<ExerciseSetData> exercises) {
        super();
        this.context = context;
        this.exercises = exercises;

    }



    public List<ExerciseSetData> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseSetData> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public void addExercise(ExerciseSetData exercise) {
        this.exercises.add(exercise);
       // notifyDataSetChanged();
        notifyItemInserted(getItemCount());
    }

    public void removeExercise(int exercise) {
        if (getItemCount() > 1) {
            this.exercises.remove(exercise);

            notifyItemRemoved(exercise);
        }

    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    @NonNull
    @Override
    public ExercisePageAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.exercisesetitem, parent, false);


        ExercisePageAdapter.ExerciseViewHolder stockViewHolder = new ExercisePageAdapter.ExerciseViewHolder(itemView, this);
        return stockViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ExercisePageAdapter.ExerciseViewHolder holder, int position) {
        ExerciseSetData exercise = exercises.get(position);

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }



    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View stockItemView;
        private ExerciseSetData exercise;

       // private int position;
        private ExercisePageAdapter adapter;

        public   EditText txtWeight;
        public EditText txtReps;

        public ExerciseViewHolder(@NonNull View itemView, ExercisePageAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
          //  position = getAdapterPosition();
            this.exercise = new ExerciseSetData();
            txtWeight = itemView.findViewById(R.id.txtWeight);
            txtReps = itemView.findViewById(R.id.txtReps);
            itemView.findViewById(R.id.btnAddExercise).setOnClickListener(this);
            itemView.findViewById(R.id.btnRemoveExercise).setOnClickListener(this);
            txtWeight.addTextChangedListener(textWatcher);
            txtReps.addTextChangedListener(textWatcher);

        }



//https://stackoverflow.com/questions/4283062/textwatcher-for-more-than-one-edittext CREDIT
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (txtReps.getText().hashCode() == editable.hashCode()) {
                    if (text.length() > 0) {
                        int rep = Integer.parseInt(text);
                        exercise.setRep(rep);
                        adapter.exercises.set(getAdapterPosition(),exercise);
                    }
                }
                if (txtWeight.getText().hashCode() == editable.hashCode()) {
                    if (text.length() > 0) {
                        double weight = Double.parseDouble(text);
                        exercise.setWeight(weight);
                        adapter.exercises.set(getAdapterPosition(),exercise);
                    }
                }
            }
        };

        @Override
        public void onClick (View view) {
          int  position = getAdapterPosition();
            ExerciseSetData exercise = this.adapter.exercises.get(position);



            if (view.getId() == R.id.btnAddExercise) {

               this.adapter.addExercise(new ExerciseSetData());
            }

            if (view.getId() == R.id.btnRemoveExercise) {

                if (getItemCount() != 1) {
                    this.adapter.removeExercise(position);
                    txtWeight.setText("");
                    txtReps.setText("");


                }

            }
        }

    }


}
