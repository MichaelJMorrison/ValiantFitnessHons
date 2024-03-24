package honours.application.valiantfitness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import honours.application.valiantfitness.chartformat.LineAxisXFormatDate;
import honours.application.valiantfitness.trackerdata.TrackerData;
import honours.application.valiantfitness.trackerdata.TrackerRepository;


public class TrackerTemplateFragment extends Fragment implements View.OnClickListener {


    public static final String ARG_MODE = "mode";

    private TextView txtObjective;

    private TextView txtCurrent;

    private Button btnObjectiveRecord;

    private TextView txtScore;

    private TextView txtAverage;

    private EditText txtNumberInput;

    private LineChart lcTrack;

    private List<TrackerData> fullTrackerData;

    private TrackerData trackerData;

    private TrackerRepository trackerRepository;

    private static final String TAG = "TrackerTemplateFragment";

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
        txtCurrent = view.findViewById(R.id.txtCurrent);
        txtAverage = view.findViewById(R.id.txtAverage);

        lcTrack = view.findViewById(R.id.lcTrack);
        lcTrack.setTouchEnabled(true);

        trackerRepository = new TrackerRepository(getContext());

        btnObjectiveRecord.setOnClickListener(this);


        if (this.mode != null) {
            fullTrackerData = trackerRepository.GetDataFromSection(this.mode);
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                trackerData = trackerRepository.GetDataFromDateMode(mode, date);

              DisplayData();
            } catch (ParseException | Error error) {
                Log.d(TAG,error.toString());
            }




        }


        if (this.mode == "Steps") {
            txtScore.setVisibility(View.VISIBLE);
            txtNumberInput.setVisibility(View.GONE);
            btnObjectiveRecord.setVisibility(View.GONE);
            txtObjective.setText("STEPS TAKEN");
        }
        if (this.mode == "Calories") {

            txtObjective.setText("CALORIES (KCAL)");
        }
        if (this.mode == "Weight") {

            txtObjective.setText("WEIGHT (KG)");
        }

        if (this.mode == "Volume") {
            txtScore.setVisibility(View.VISIBLE);
            txtNumberInput.setVisibility(View.GONE);
            btnObjectiveRecord.setVisibility(View.GONE);
            txtObjective.setText("TOTAL VOLUME LIFTED (KG)");

        }

    }

    public void DisplayData(){
        if (trackerData != null){
            txtCurrent.setText(trackerData.getValue().toString());
            txtScore.setText(trackerData.getValue().toString());

        }
        if(fullTrackerData.size() > 0) {
            txtAverage.setText(GetAverage().toString());
            LoadChart();
        }

    }

    private Double GetAverage(){

        Double average = 0.0;

        for (TrackerData TrackerDataIndividual:fullTrackerData
             ) {

            average+= TrackerDataIndividual.getValue();

        }

        return average/fullTrackerData.size();
    }
    public void LoadChart() {
    List<Entry> entries =  new ArrayList<>();
    List<String> dates = new ArrayList<>();
        Log.d(TAG, Integer.toString(fullTrackerData.size()));
    try {
        for (int i = 0; i < fullTrackerData.size(); i++) {
            //  entries.add(new Entry(fullTrackerData.get(i).getDate().getTime(),fullTrackerData.get(i).getValue().floatValue()));
            entries.add(new Entry(i, fullTrackerData.get(i).getValue().floatValue()));

            try {
                Log.d(TAG,fullTrackerData.get(i).getDate().toString());
                dates.add(new SimpleDateFormat("dd/MM/yyyy").format(fullTrackerData.get(i).getDate()).toString());

            } catch (Error error) {
                error.printStackTrace();
                Log.d(TAG, "ERROR");
            }


        }

        Log.d(TAG, "Phase 1 Passed");

        LineDataSet lineDataSet = new LineDataSet(entries, mode);


        lineDataSet.setColor(Color.rgb(66, 113, 255));
        lineDataSet.setCircleRadius(4);
        lineDataSet.setCircleColor(Color.rgb(66, 113, 255));

        LineData lineData = new LineData(lineDataSet);
        lineDataSet.setLineWidth(2);
        lineData.setDrawValues(false);
        Log.d(TAG, "Phase 2 Passed");
        lcTrack.setData(lineData);
        lcTrack.getXAxis().setValueFormatter(new LineAxisXFormatDate(dates));
        Log.d(TAG, "Phase 3 Passed");
        lcTrack.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        //lcTrack.invalidate();

        lcTrack.setDrawGridBackground(false);
        lcTrack.getXAxis().setDrawLimitLinesBehindData(true);
        lcTrack.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lcTrack.getDescription().setEnabled(false);
        lcTrack.getXAxis().setDrawGridLines(false);
        lcTrack.getXAxis().setDrawAxisLine(true);
        lcTrack.getAxisLeft().setDrawGridLines(false);
        lcTrack.getAxisLeft().setDrawAxisLine(true);
        lcTrack.getAxisRight().setEnabled(false);
        lcTrack.notifyDataSetChanged();
        lcTrack.invalidate();

        if(fullTrackerData.size() == 1){
            lcTrack.getXAxis().setGranularity(2F);
        }else{
            lcTrack.getXAxis().setGranularity(1F);
        }



        lcTrack.getLegend().setEnabled(false);
        lcTrack.setHighlightPerDragEnabled(false);

        lcTrack.getXAxis().setTypeface(Typeface.DEFAULT_BOLD);
        lcTrack.getAxisLeft().setTypeface(Typeface.DEFAULT_BOLD);
        Log.d(TAG, "Phase 4 Passed");
    }catch (Error error){
        Log.d(TAG,error.toString());
    }

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

            Date date;

            if (txtNumberInput.getText().length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Please make sure a numerical value has been inputted before proceeding");
                builder.setTitle("Tracker Record");
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

                    if (trackerData == null) {
                        trackerData = new TrackerData();
                        trackerData.setDataName(mode);
                        trackerData.setDate(date);
                        trackerData.setValue(Double.parseDouble(txtNumberInput.getText().toString()));
                        try {
                            trackerRepository.AddTrackedData(trackerData);
                        } catch (Error error) {
                            Log.d(TAG, error.toString());
                        } finally {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Data Saved Successfully!");
                            builder.setTitle("Tracker Record");
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    fullTrackerData = trackerRepository.GetDataFromSection(mode);
                                    DisplayData();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();


                        }


                    } else {
                        Log.d(TAG, txtNumberInput.getText().toString());
                        trackerData.setValue(Double.parseDouble(txtNumberInput.getText().toString()));

                        try {
                            trackerRepository.UpdateTrackedData(trackerData);
                        } catch (Error error) {
                            Log.d(TAG, error.toString());
                        } finally {


                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Data Updated Successfully!");
                            builder.setTitle("Tracker Record");
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    fullTrackerData = trackerRepository.GetDataFromSection(mode);
                                    DisplayData();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }


                } catch (ParseException | Error error) {
                    error.printStackTrace();
                }
            }


        }
    }
}