package honours.application.valiantfitness.recyclerviewadapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseRepository;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;
import honours.application.valiantfitness.exercisedata.ExerciseSetRepository;

public class ExerciseHistoryAdapter extends RecyclerView.Adapter<ExerciseHistoryAdapter.ExerciseViewHolder> {
    private Context context;
    private List<ExerciseData> exercises;
    private static final String TAG = "ExerciseHistoryAdapter";

    public ExerciseHistoryAdapter(Context context,List<ExerciseData> exercises) {
        super();
        this.context = context;
        this.exercises = exercises;

    }

    @NonNull
    @Override
    public ExerciseHistoryAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.tab_exercise_history_item, parent, false);


        ExerciseHistoryAdapter.ExerciseViewHolder exerciseViewHolder = new ExerciseHistoryAdapter.ExerciseViewHolder(itemView, this);
        return exerciseViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHistoryAdapter.ExerciseViewHolder holder, int position) {
        ExerciseData exercise = exercises.get(position);
        TextView txtDate = holder.itemView.findViewById(R.id.txt_rv_date);
       // txtDate.setText(exercise.getDate().toString());

        txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(exercise.getDate()).toString());
        ExerciseSetRepository exerciseRepository = new ExerciseSetRepository(this.context);
        Log.d(TAG, exercise.toString());
        List<ExerciseSetData> setData = exerciseRepository.GetAllExerciseSetDataFromExerciseID(exercise.getID());
       // List<ExerciseSetData> setData = exerciseRepository.getAllExerciseSetData();
        ExerciseHistorySetAdapter RVAdapter = new ExerciseHistorySetAdapter(holder.adapter.context,setData);

        RecyclerView recyclerView = holder.itemView.findViewById(R.id.rv_exercise_history_set);

        recyclerView.setAdapter(RVAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(holder.adapter.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

      Log.d(TAG, setData.toString());

    }
    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private View exerciseViewItem;

        private ExerciseHistoryAdapter adapter;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseHistoryAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.exerciseViewItem = itemView;


        }
    }


    @Override
    public int getItemCount() {
        return this.exercises.size();
    }
}
