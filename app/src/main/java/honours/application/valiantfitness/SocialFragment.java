package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment implements View.OnClickListener{


    List<String> spinnerArray =  new ArrayList<String>();
    Button btnFind;

    Button btnSearch;

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
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnFind = view.findViewById(R.id.btnFind);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnFind.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == btnFind.getId()){

         if(ValidationPass() == true){

         }else{

         }

        }

    }

    public Boolean ValidationPass(){


        return true;
    };

}