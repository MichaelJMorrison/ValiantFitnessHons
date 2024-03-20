package honours.application.valiantfitness;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.concurrent.ExecutionException;

import honours.application.valiantfitness.exercisecategory.Exercise;
import honours.application.valiantfitness.userdata.User;
import honours.application.valiantfitness.userdata.UserRepository;
import honours.application.valiantfitness.workoutdata.WorkoutData;
import honours.application.valiantfitness.workoutdata.WorkoutExerciseRepository;
import honours.application.valiantfitness.workoutdata.WorkoutRepository;


public class ProfileSettingsFragment extends Fragment implements View.OnClickListener {

    private EditText txtEditUserName;
    private EditText txtEditBio;
    private EditText txtEditName;

    private static final String TAG = "ProfileSettings";

    private Bitmap image;

    private String DeviceID;
    private ImageButton btnPictureSelect;

    private Button btnProfileSave;
    private Button btnProfileCancel;

    //https://developer.android.com/training/data-storage/shared/photopicker CREDIT TO ALL IMAGE SELECTOR STUFF IN THIS FRAGMENT
    //https://medium.com/@everydayprogrammer/implement-android-photo-picker-in-android-studio-3562a85c85f1 Reference for Glide
    //Credit to Glide Package https://bumptech.github.io/glide/doc/download-setup.html
    //https://akshayranagujjar.medium.com/how-to-save-image-to-storage-using-glide-in-android-fa26c842f212
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {

                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    try {
                        Glide.with(getActivity().getApplicationContext()).asBitmap().load(uri).into(btnPictureSelect);

                        Glide.with(getActivity().getApplicationContext()).load(uri).into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                                image = ((BitmapDrawable) resource).getBitmap();
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });

                    }catch (Error  error){

                    }

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
        this.DeviceID = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
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
                if(ValidationPass() == true) {
                    Log.d(TAG, "Validation Successful, Saving Time");
                    try{
                        UserRepository userRepository = new UserRepository(getContext());

                        User user;

                        if(userRepository.GetUserFromDeviceID(this.DeviceID) != null) {
                            user = userRepository.GetUserFromDeviceID(this.DeviceID);
                            user.setDeviceID(this.DeviceID);
                            user.setName(txtEditName.getText().toString());
                            user.setUserName(txtEditUserName.getText().toString());
                            user.setBiography(txtEditBio.getText().toString());
                            user.setProfileImage(this.image);
                            userRepository.UpdateUser(user);
                        }else{
                            user = new User();
                            user.setDeviceID(this.DeviceID);
                            user.setName(txtEditName.getText().toString());
                            user.setUserName(txtEditUserName.getText().toString());
                            user.setBiography(txtEditBio.getText().toString());
                            user.setProfileImage(this.image);
                            userRepository.AddUser(user);
                        }



                    }catch (Error error) {
                        error.printStackTrace();
                    }finally {
                        Log.d(TAG, "Profile Saved");
                    }
                }
               break;
           default:
               break;
       }

    }
    public boolean ValidationPass(){



        if(txtEditName.getText().length()==0) {
            Log.d(TAG, "txtEditName is empty");
            return false;
        }

        if(txtEditBio.getText().length()==0) {
            Log.d(TAG, "txtEditBio is empty");
            return false;
        }

        if(txtEditUserName.getText().length()==0) {
            Log.d(TAG, "txtEditUserName is empty");
            return false;
        }

        if(image == null) {
            Log.d(TAG, "No media selected");
            return false;
        }

        return true;
    };
}