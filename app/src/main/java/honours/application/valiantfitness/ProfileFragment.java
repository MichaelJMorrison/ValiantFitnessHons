package honours.application.valiantfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import honours.application.valiantfitness.userdata.User;
import honours.application.valiantfitness.userdata.UserRepository;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ImageButton imageButton;

    private ImageView imageProfile;
    private User user;

    private TextView txtUsername;

    private TextView txtName;

    private TextView txtBio;


    String DeviceID;
    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.imageButton = view.findViewById(R.id.btnSettings);

        this.imageButton.setOnClickListener(this);

        this.txtName = view.findViewById(R.id.txtName);
        this.txtBio = view.findViewById(R.id.txtBio);
        this.txtUsername = view.findViewById(R.id.txtUsername);
        this.imageProfile = view.findViewById(R.id.imageProfile);

        try {
            UserRepository userRepository = new UserRepository(getContext());
            if(userRepository.GetUserFromDeviceID(this.DeviceID) != null) {
                this.user = userRepository.GetUserFromDeviceID(this.DeviceID);
                this.txtUsername.setText(this.user.getUserName());
                this.txtBio.setText(this.user.getBiography());
                this.txtName.setText(this.user.getName());
                Glide.with(view).load(this.user.getProfileImage()).into(this.imageProfile);

            }
        }catch (Error error) {

        }

    }
    @Override
    public void onClick(View view) {

       if (view.getId() == R.id.btnSettings) {

           AppCompatActivity activity = (AppCompatActivity) getContext();
           Bundle bundle = new Bundle();

           ProfileSettingsFragment profileSettingsFragment = new ProfileSettingsFragment();


           activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, profileSettingsFragment).commit();

       }

    }

}