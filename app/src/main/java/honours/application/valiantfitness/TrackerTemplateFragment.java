package honours.application.valiantfitness;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        lcTrack.setPinchZoom(true);
        trackerRepository = new TrackerRepository(getContext());

        btnObjectiveRecord.setOnClickListener(this);


        if (this.mode != null) {
            fullTrackerData = trackerRepository.GetDataFromSection(this.mode);
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                trackerData = trackerRepository.GetDataFromDateMode(mode, date);
            } catch (ParseException error) {

            }

            if (trackerData != null){
                txtCurrent.setText(trackerData.getValue().toString());
                txtAverage.setText(GetAverage().toString());
                LoadChart();
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
        for (int i = 0; i < fullTrackerData.size(); i++) {
         //  entries.add(new Entry(fullTrackerData.get(i).getDate().getTime(),fullTrackerData.get(i).getValue().floatValue()));
            entries.add(new Entry(i,fullTrackerData.get(i).getValue().floatValue()));

            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dates.add(new SimpleDateFormat("dd/MM/yyyy").format(fullTrackerData.get(i).getDate()).toString());
                Log.d(TAG,dates.get(i));
            }catch (Error error) {
                Log.d(TAG,"ERROR");
            }


        }

        LineDataSet lineDataSet = new LineDataSet(entries,mode);

        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineData lineData = new LineData(lineDataSet);


        lcTrack.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        lcTrack.setData(lineData);
        lcTrack.invalidate();
         lcTrack.getXAxis().setValueFormatter(new LineAxisXFormatDate(dates));

        lcTrack.getXAxis().setDrawLimitLinesBehindData(true);
        lcTrack.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

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
                            builder.setMessage("Data Saved Successfully!");
                            builder.setTitle("Tracker Record");
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

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