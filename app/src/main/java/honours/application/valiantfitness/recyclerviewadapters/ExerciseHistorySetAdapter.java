package honours.application.valiantfitness.recyclerviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;

public class ExerciseHistorySetAdapter extends RecyclerView.Adapter<ExerciseHistorySetAdapter.ExerciseViewHolder> {

    private Context context;
    private List<ExerciseSetData> setData;
    private static final String TAG = "ExerciseHistorySetAdapter";

    public ExerciseHistorySetAdapter(Context context,List<ExerciseSetData> setData) {
        super();
        this.context = context;
        this.setData = setData;

    }

    @NonNull
    @Override
    public ExerciseHistorySetAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.tab_exercise_history_set_item, parent, false);


        ExerciseHistorySetAdapter.ExerciseViewHolder exerciseViewHolder = new ExerciseHistorySetAdapter.ExerciseViewHolder(itemView, this);
        return exerciseViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHistorySetAdapter.ExerciseViewHolder holder, int position) {
        ExerciseSetData exercise = setData.get(position);
        TextView txtW = holder.itemView.findViewById(R.id.txt_rv_setWeight);
        TextView txtR = holder.itemView.findViewById(R.id.txt_rv_setReps);
        TextView txtS = holder.itemView.findViewById(R.id.txt_rv_setID);
        txtW.setText(Double.toString(exercise.getWeight()));
        txtR.setText(Integer.toString(exercise.getRep()));
        txtS.setText(Integer.toString(position+1));
    }
    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private View exerciseViewItem;

        private ExerciseHistorySetAdapter adapter;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseHistorySetAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.exerciseViewItem = itemView;

        }
    }


    @Override
    public int getItemCount() {
        return this.setData.size();
    }


}
