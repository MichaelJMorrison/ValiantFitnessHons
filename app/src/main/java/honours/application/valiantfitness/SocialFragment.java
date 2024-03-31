package honours.application.valiantfitness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
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

import honours.application.valiantfitness.socialdata.SocialData;
import honours.application.valiantfitness.userdata.User;
import honours.application.valiantfitness.userdata.UserRepository;

public class SocialFragment extends Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener {


    List<String> spinnerArray =  new ArrayList<String>();
    Button btnFind;

    Button btnSearch;

    EditText txtSocialDescription;

    EditText txtSearchLocation;

    Spinner spinner;

    String DeviceID = "";

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
        DeviceID = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
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

        UserRepository userRepository = new UserRepository(getContext());
        User user = new User();
        try{
            user = userRepository.GetUserFromDeviceID(DeviceID);
        }catch (Error error){

        }


        if(view.getId() == btnSearch.getId()){

         if(ValidationPass() == true){
             Log.d(TAG, "Validation Passed");



             SocialData socialData = new SocialData();
             socialData.setMode(0);
             socialData.setDescription(txtSocialDescription.getText().toString());
             socialData.setLocation(txtSearchLocation.getText().toString());
             socialData.setProfile(user.getUserName());

             Bundle bundle = new Bundle();
             bundle.putInt(SocialHelpFragment.ARG_MODE,1);
             bundle.putParcelable(SocialHelpFragment.ARG_DATA,socialData);
             SocialHelpFragment socialHelpFragment = new SocialHelpFragment();
             socialHelpFragment.setArguments(bundle);
             activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, socialHelpFragment).commit();


         }else{
             Log.d(TAG, "Validation Failed");
             AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
             builder.setMessage("Please make sure a profile has been set up, and all details have been filled in");
             builder.setTitle("Social Search Failed");
             builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {

                 }
             });
             AlertDialog dialog = builder.create();
             dialog.show();

         }

        } else if (view.getId() == btnFind.getId()){

            if(ValidationPass2() == true){

                SocialData socialData = new SocialData();
                socialData.setMode(0);
                socialData.setDescription(txtSocialDescription.getText().toString());
                socialData.setLocation(txtSearchLocation.getText().toString());
                socialData.setProfile(user.getUserName());

                Bundle bundle = new Bundle();
                bundle.putInt(SocialHelpFragment.ARG_MODE,0);
                bundle.putParcelable(SocialHelpFragment.ARG_DATA,socialData);
                SocialHelpFragment socialHelpFragment = new SocialHelpFragment();
                socialHelpFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, socialHelpFragment).commit();
            }else {
                Log.d(TAG, "Validation Failed");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Please make sure a profile has been set up before proceeding");
                builder.setTitle("Social Search Failed");
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

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

        UserRepository userRepository = new UserRepository(getContext());

        if (userRepository.GetUserFromDeviceID(DeviceID) == null){
            return false;
        }

        return true;
    };


    public Boolean ValidationPass2(){


        UserRepository userRepository = new UserRepository(getContext());

        if (userRepository.GetUserFromDeviceID(DeviceID) == null){
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