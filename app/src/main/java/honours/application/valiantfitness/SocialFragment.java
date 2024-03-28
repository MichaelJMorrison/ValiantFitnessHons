package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener {


    List<String> spinnerArray =  new ArrayList<String>();
    Button btnFind;

    Button btnSearch;

    EditText txtSocialDescription;

    EditText txtSearchLocation;

    Spinner spinner;

    private int spinnerLoc = 0;

    private static final String TAG = "SocialFragment";

    public SocialFragment() {
        // Required empty public constructor
    }


    public static SocialFragment newInstance(String param1, String param2) {
        SocialFragment fragment = new SocialFragment();
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
        return inflater.inflate(R.layout.fragment_social, container, false);
    }
    public void loadSpinnerArray() {
        spinnerArray.add("-SELECT-");
        spinnerArray.add("Need a spotter");
        spinnerArray.add("Need a guide");


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadSpinnerArray();
        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        txtSocialDescription = view.findViewById(R.id.txtSocialDescription);
        txtSearchLocation = view.findViewById(R.id.txtSearchLocation);
        btnFind = view.findViewById(R.id.btnFind);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnFind.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        if(view.getId() == btnSearch.getId()){

         if(ValidationPass() == true){
             Log.d(TAG, "Validation Passed");

             Bundle bundle = new Bundle();
             bundle.putInt(SocialHelpFragment.ARG_MODE,1);
             SocialHelpFragment socialHelpFragment = new SocialHelpFragment();
             socialHelpFragment.setArguments(bundle);
             activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, socialHelpFragment).commit();


         }else{
             Log.d(TAG, "Validation Failed");
         }

        } else if (view.getId() == btnFind.getId()){
            Bundle bundle = new Bundle();
            bundle.putInt(SocialHelpFragment.ARG_MODE,0);
            SocialHelpFragment socialHelpFragment = new SocialHelpFragment();
            socialHelpFragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, socialHelpFragment).commit();
        }

    }

    public Boolean ValidationPass(){

        if(txtSearchLocation.getText().length() == 0){
            return false;
        } else if (txtSocialDescription.getText().length() == 0) {
            return false;
        } else if (spinnerLoc == 0) {
            return false;
        }

        return true;
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerLoc = i;
        Log.d(TAG, Integer.toString(spinnerLoc));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}