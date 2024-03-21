package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;

import org.w3c.dom.Text;

import java.util.List;

import honours.application.valiantfitness.trackerdata.TrackerData;
import honours.application.valiantfitness.trackerdata.TrackerRepository;


public class TrackerTemplateFragment extends Fragment implements View.OnClickListener {


    public static final String ARG_MODE = "mode";

    private TextView txtObjective;

    private Button btnObjectiveRecord;

    private TextView txtScore;

    private EditText txtNumberInput;

    private LineChart lcTrack;

    private List<TrackerData> fullTrackerData;

    private TrackerData trackerData;

    private TrackerRepository trackerRepository;

    private String mode;

    public TrackerTemplateFragment() {
        // Required empty public constructor
    }

    public static TrackerTemplateFragment newInstance(String mode) {
        TrackerTemplateFragment fragment = new TrackerTemplateFragment();
        Bundle args = new Bundle();

        args.putString(ARG_MODE, mode);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            this.mode = args.getString(ARG_MODE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtObjective = view.findViewById(R.id.txtObjective);
        txtScore = view.findViewById(R.id.txtScore);
        btnObjectiveRecord = view.findViewById(R.id.btnObjectiveRecord);
        txtNumberInput = view.findViewById(R.id.txtNumberInput);
        lcTrack = view.findViewById(R.id.lcTrack);
        lcTrack.setTouchEnabled(true);

        trackerRepository = new TrackerRepository(getContext());

        btnObjectiveRecord.setOnClickListener(this);


        if(this.mode != null){
            fullTrackerData = trackerRepository.GetDataFromSection(this.mode);

        }


       if (this.mode == "Steps"){
            txtScore.setVisibility(View.VISIBLE);
            txtNumberInput.setVisibility(View.GONE);
           btnObjectiveRecord.setVisibility(View.GONE);
            txtObjective.setText("STEPS TAKEN");
        }
        if (this.mode == "Calories"){

            txtObjective.setText("CALORIES (KCAL)");
        }
        if (this.mode == "Weight"){

            txtObjective.setText("WEIGHT (KG)");
        }

    }

    public void LoadChart(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracker_template, container, false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnObjectiveRecord) {




        }
    }
}