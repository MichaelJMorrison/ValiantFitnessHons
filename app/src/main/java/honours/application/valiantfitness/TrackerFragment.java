package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import honours.application.valiantfitness.exercisedata.ExerciseData;
import honours.application.valiantfitness.exercisedata.ExerciseRepository;
import honours.application.valiantfitness.exercisedata.ExerciseSetComparator;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;
import honours.application.valiantfitness.exercisedata.ExerciseSetRepository;
import honours.application.valiantfitness.trackerdata.TrackerData;
import honours.application.valiantfitness.trackerdata.TrackerRepository;


public class TrackerFragment extends Fragment implements View.OnClickListener{


    private Button WeightButton;

    private Button CaloriesButton;

    private  Button StepsButton;

    private  Button VolumeButton;

    private TextView txtExercise2;
    private  TextView txtExercise2Score;
    private TextView txtExercise1Score;
    private  TextView txtExercise1;
    private  TextView txtExercise3Score;
    private  TextView txtExercise3;

    ExerciseRepository exerciseRepository;
    ExerciseSetRepository exerciseSetRepository;

    private TrackerRepository trackerRepository;

    private static final String TAG = "TrackerFragment";

    public TrackerFragment() {
        // Required empty public constructor
    }

    public static TrackerFragment newInstance(String param1, String param2) {
        TrackerFragment fragment = new TrackerFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.CaloriesButton = view.findViewById(R.id.CaloriesButton);
        this.WeightButton = view.findViewById(R.id.WeightButton);
        this.StepsButton = view.findViewById(R.id.StepsButton);
        this.VolumeButton = view.findViewById(R.id.VolumeButton);
        this.CaloriesButton.setOnClickListener(this);
        this.WeightButton.setOnClickListener(this);
        this.StepsButton.setOnClickListener(this);
        this.VolumeButton.setOnClickListener(this);

        this.txtExercise1 = view.findViewById(R.id.txtExercise1);
        this.txtExercise1Score = view.findViewById(R.id.txtExercise1Score);
        this.txtExercise2 = view.findViewById(R.id.txtExercise2);
        this.txtExercise2Score = view.findViewById(R.id.txtExercise2Score);
        this.txtExercise3 = view.findViewById(R.id.txtExercise3);
        this.txtExercise3Score = view.findViewById(R.id.txtExercise3Score);



        trackerRepository = new TrackerRepository(getContext());

        try{

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            TrackerData trackerData = trackerRepository.GetDataFromDateMode("Calories",date);

            if(trackerData != null){
                TextView textView = view.findViewById(R.id.CaloriesValue);
                textView.setText(trackerData.getValue().toString());
                TextView textView1 = view.findViewById(R.id.CaloriesAverage);
                Double rounded = (double) Math.round(GetAverage()*100)/100;
                textView1.setText(rounded.toString());
            }

            trackerData = trackerRepository.GetDataFromDateMode("Weight",date);

            TextView txtDate= view.findViewById(R.id.DateWeight);
            txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString());

            if(trackerData != null){
                TextView textView = view.findViewById(R.id.WeightValue);
                textView.setText(trackerData.getValue().toString());

            }

            trackerData = trackerRepository.GetDataFromDateMode("Volume",date);

            if(trackerData != null){
                TextView textView = view.findViewById(R.id.txtVolume);
                textView.setText(trackerData.getValue().toString());
                ProgressBar volumeBar = view.findViewById(R.id.VolumeProgressBar);

                Date date1 =  simpleDateFormat.parse(simpleDateFormat.format(date.getTime() - 24 * 60 * 60 * 1000));
                TrackerData trackerData1 = trackerRepository.GetDataFromDateMode("Volume",date1);
                if (trackerData1 != null) {
                    volumeBar.setProgress((int) Math.abs((trackerData.getValue()/trackerData1.getValue())*100));
                }else {
                    volumeBar.setProgress((int) Math.abs(trackerData.getValue()));
                }


            }
            DisplayPersonalBest();
            trackerData = trackerRepository.GetDataFromDateMode("Steps",date);

            if(trackerData != null){
                TextView textView = view.findViewById(R.id.txtSteps);
                textView.setText(trackerData.getValue().toString() + "/ 7500");
                ProgressBar volumeBar = view.findViewById(R.id.VolumeProgressBar);
                volumeBar.setProgress((int) Math.abs(trackerData.getValue()/7500));

            }

        }catch (Error error){

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    public void DisplayPersonalBest(){
        exerciseRepository = new ExerciseRepository(getContext());
        exerciseSetRepository = new ExerciseSetRepository(getContext());
        List<ExerciseData> exerciseDataList = exerciseRepository.GetAllExercises();

        if(exerciseDataList != null){



        int limit = exerciseDataList.size();
        if(exerciseDataList.size() > 3){
            limit = 3;
        }

        List<TextView> exerciseTitles = new ArrayList<>();
        exerciseTitles.add(txtExercise1);
        exerciseTitles.add(txtExercise2);
        exerciseTitles.add(txtExercise3);
        List<TextView> exerciseValues = new ArrayList<>();
        exerciseValues.add(txtExercise1Score);
        exerciseValues.add(txtExercise2Score);
        exerciseValues.add(txtExercise3Score);

        Collections.sort(exerciseDataList,Collections.reverseOrder(new Comparator<ExerciseData>() {
            @Override
            public int compare(ExerciseData exerciseData, ExerciseData t1) {
                List<ExerciseSetData> exerciseSetData = exerciseSetRepository.GetAllExerciseSetDataFromExerciseID(exerciseData.getID());
                List<ExerciseSetData> exerciseSetData2 = exerciseSetRepository.GetAllExerciseSetDataFromExerciseID(t1.getID());

                ExerciseSetComparator exerciseSetComparator = new ExerciseSetComparator();
                Collections.sort(exerciseSetData, exerciseSetComparator);
                Collections.sort(exerciseSetData2, exerciseSetComparator);

                return Double.compare(exerciseSetData.get(0).getWeight(),exerciseSetData2.get(0).getWeight());
            }
        }));


        for (int i = 0; i < limit; i++) {
            exerciseTitles.get(i).setText(exerciseDataList.get(i).getName());
            exerciseValues.get(i).setText(Double.toString(GetPersonalData(exerciseDataList.get(i)).getWeight()) + " kg");

        }

        }
    }

    public ExerciseSetData GetPersonalData(ExerciseData exerciseData){


        List<ExerciseSetData> exerciseSetData;
        exerciseSetData = exerciseSetRepository.GetAllExerciseSetDataFromExerciseID(exerciseData.getID());
        Collections.sort(exerciseSetData,Collections.reverseOrder(new ExerciseSetComparator()));

        return exerciseSetData.get(0) ;

    }

    public Double GetAverage(){

        Double average = 0.0;

        List<TrackerData> fullTrackerData = trackerRepository.GetDataFromSection("Calories");

        if(fullTrackerData.size()>7){
            for (int i = fullTrackerData.lastIndexOf(fullTrackerData)-7; i < fullTrackerData.lastIndexOf(fullTrackerData); i++) {
                average+= fullTrackerData.get(i).getValue();
            }
            return average/7;
        }else{
            for (TrackerData TrackerDataIndividual:fullTrackerData
            ) {

                average+= TrackerDataIndividual.getValue();

            }

            return average/fullTrackerData.size();
        }


    }

    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        Bundle bundle = new Bundle();

       switch (view.getId()){
           case R.id.CaloriesButton:
               bundle.putString(TrackerTemplateFragment.ARG_MODE,"Calories");
               break;
           case R.id.StepsButton:
               bundle.putString(TrackerTemplateFragment.ARG_MODE,"Steps");
               break;
           case R.id.WeightButton:
               bundle.putString(TrackerTemplateFragment.ARG_MODE,"Weight");
               break;
           case R.id.VolumeButton:
               bundle.putString(TrackerTemplateFragment.ARG_MODE,"Volume");
               break;
       }

        TrackerTemplateFragment trackerTemplateFragment = new TrackerTemplateFragment();
        trackerTemplateFragment.setArguments(bundle);

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, trackerTemplateFragment).commit();
    }
}