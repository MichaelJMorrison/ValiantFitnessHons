package honours.application.valiantfitness;

import android.media.Image;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class ProfileSettingsFragment extends Fragment implements View.OnClickListener {

    private EditText txtEditUserName;
    private EditText txtEditBio;
    private EditText txtEditName;

    private Image image;
    private ImageButton btnPictureSelect;

    private Button btnProfileSave;
    private Button btnProfileCancel;

    //https://developer.android.com/training/data-storage/shared/photopicker CREDIT TO ALL IMAGE SELECTOR STUFF IN THIS FRAGMENT
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);

                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });
    public ProfileSettingsFragment() {
        // Required empty public constructor
    }



    public static ProfileSettingsFragment newInstance(String param1, String param2) {
        ProfileSettingsFragment fragment = new ProfileSettingsFragment();
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
        return inflater.inflate(R.layout.fragment_profile_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnProfileCancel = view.findViewById(R.id.btnProfileCancel);
        btnPictureSelect = view.findViewById(R.id.btnPictureSelect);
        btnProfileSave = view.findViewById(R.id.btnProfileSave);

        txtEditBio = view.findViewById(R.id.txtEditBio);
        txtEditName = view.findViewById(R.id.txtEditName);
        txtEditUserName = view.findViewById(R.id.txtEditUserName);

        btnProfileCancel.setOnClickListener(this);
        btnPictureSelect.setOnClickListener(this);
        btnProfileSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

       switch (view.getId()) {
           case R.id.btnProfileCancel:

               break;
           case R.id.btnPictureSelect:
               pickMedia.launch(new PickVisualMediaRequest.Builder()
                       .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                       .build());

               break;
           case R.id.btnProfileSave:

               break;
           default:
               break;
       }

    }
}