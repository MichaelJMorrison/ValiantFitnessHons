package honours.application.valiantfitness;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pServiceRequest;
import android.net.wifi.p2p.nsd.WifiP2pUpnpServiceRequest;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import honours.application.valiantfitness.badge.Badge;
import honours.application.valiantfitness.badge.BadgeRepository;
import honours.application.valiantfitness.recyclerviewadapters.DeviceAdapter;
import honours.application.valiantfitness.recyclerviewadapters.DeviceAdapterV2;
import honours.application.valiantfitness.socialdata.SocialData;
import honours.application.valiantfitness.socialdata.SocialDataConfined;
import honours.application.valiantfitness.socialdata.WifiBroadcast;
import honours.application.valiantfitness.trackerdata.TrackerData;
import honours.application.valiantfitness.trackerdata.TrackerRepository;
import honours.application.valiantfitness.workoutdata.WorkoutRepository;


public class SocialHelpFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_MODE = "mode";

    public static final String ARG_DATA = "data";

    private static final String TAG = "SocialHelpFragment";

    //https://developer.android.com/develop/connectivity/wifi/wifi-direct#receiver For all the wifi related work for this
    private final IntentFilter intentFilter = new IntentFilter();

    WifiP2pManager manager;

    WifiP2pManager.Channel channel;

    private TextView txtSearchInfo;

    private Button btnSocialClose;

    private RecyclerView rv_Search;

    private WifiBroadcast broadcastReceiver;

    private int mode;

    private SocialData socialData;


    final HashMap<String, String> usernames = new HashMap<String, String>();

    final HashMap<String, String> descriptions = new HashMap<String, String>();

    private List<SocialDataConfined> socialDataConfinedList = new ArrayList<>();


    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),

            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean o) {
                    if (o) {

                    }
                }
            }


    );


    public SocialHelpFragment() {
        // Required empty public constructor
    }


    public static SocialHelpFragment newInstance(int mode, SocialData socialData) {
        SocialHelpFragment fragment = new SocialHelpFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MODE, mode);
        args.putParcelable(ARG_DATA, socialData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + getArguments());
        if (getArguments() != null) {
            Bundle args = getArguments();
            this.mode = args.getInt(ARG_MODE);
            this.socialData = args.getParcelable(ARG_DATA);
            Log.d(TAG, Integer.toString(mode));
        }
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        MainActivity activity = (MainActivity) getContext();

//https://stackoverflow.com/questions/19102948/initialize-method-to-register-with-wifip2p-framework-in-fragment
        manager = (WifiP2pManager) getActivity().getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(getContext(), getContext().getMainLooper(), null);
        broadcastReceiver = new WifiBroadcast(manager, channel, activity, this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        txtSearchInfo = view.findViewById(R.id.txtSearchInfo);
        btnSocialClose = view.findViewById(R.id.btnSocialClose);
        btnSocialClose.setOnClickListener(this);
        rv_Search = view.findViewById(R.id.rv_Search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_Search.setLayoutManager(layoutManager);
        MainActivity activity = (MainActivity) getContext();
        if (mode == 0) {
            txtSearchInfo.setText("PLEASE WAIT WHILE WE FIND PEOPLE TO HELP");
        } else {
            txtSearchInfo.setText("PLEASE WAIT WHILE WE FIND SOMEONE TO HELP YOU");
            rv_Search.setVisibility(View.GONE);
        }



        DiscoverPeers();



    }

    public void DiscoverPeers(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //https://stackoverflow.com/questions/66551781/android-onrequestpermissionsresult-is-deprecated-are-there-any-alternatives CREDIT FOR REQUEST PERMISSION LAUNCH
            //requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Manifest.permission.ACCESS_FINE_LOCATION.hashCode());
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.NEARBY_WIFI_DEVICES);
        }
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "SOMEONE HAS BEEN FOUND");
            }

            @Override
            public void onFailure(int i) {
                Log.d(TAG, "NO ONE FOUND");
            }
        });
    }

    public void DeviceListRender(List<WifiP2pDevice> deviceList) {
        Log.d(TAG, "DeviceListRender:" + Integer.toString(deviceList.size()));
        if (deviceList.size() > 0 && mode == 0) {
            txtSearchInfo.setVisibility(View.GONE);
        } else {
            txtSearchInfo.setVisibility(View.VISIBLE);
        }
        DeviceAdapter deviceAdapter = new DeviceAdapter(getContext(), deviceList, this);
        rv_Search.setAdapter(deviceAdapter);


    }

    public void DeviceListRender2() {
        Log.d(TAG, "Rendering");
        if (socialDataConfinedList.size() > 0 && mode == 0) {
            txtSearchInfo.setVisibility(View.GONE);
        } else {
            txtSearchInfo.setVisibility(View.VISIBLE);
        }
        DeviceAdapterV2 deviceAdapter = new DeviceAdapterV2(getContext(), socialDataConfinedList, this);
        rv_Search.setAdapter(deviceAdapter);


    }


    public void InformUser() {
        btnSocialClose.setVisibility(View.VISIBLE);
        rv_Search.setVisibility(View.GONE);
        if (txtSearchInfo.getVisibility() == View.GONE) {
            txtSearchInfo.setVisibility(View.VISIBLE);
        }
        disconnect();

        if (this.mode == 0) {
            txtSearchInfo.setText("THANK YOU FOR HELPING A USER");
        } else {
            txtSearchInfo.setText("HELP IS ON THE WAY");
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_help, container, false);

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    public WifiP2pManager getManager() {
        return manager;
    }

    public void setManager(WifiP2pManager manager) {
        this.manager = manager;
    }

    public WifiP2pManager.Channel getChannel() {
        return channel;
    }

    public void setChannel(WifiP2pManager.Channel channel) {
        this.channel = channel;
    }











    public void disconnect() {
        //REFERENCE CREDIT TO https://stackoverflow.com/questions/18679481/wifi-direct-end-connection-to-peer-on-android

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
        }
        manager.requestGroupInfo(channel, new WifiP2pManager.GroupInfoListener() {
            @Override
            public void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
                if (wifiP2pGroup != null && manager != null & channel != null) {
                    manager.removeGroup(channel, new WifiP2pManager.ActionListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Successfully removed");
                        }

                        @Override
                        public void onFailure(int i) {

                        }
                    });
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (AppCompatActivity) getContext();

        TrackerRepository trackerRepository = new TrackerRepository(getContext());
        TrackerData trackerData;





        try{
            if(trackerRepository.GetSingleDataFromSection("Social") != null){
                trackerData = trackerRepository.GetSingleDataFromSection("Social");
                trackerData.setValue(trackerData.getValue()+1);
                trackerRepository.UpdateTrackedData(trackerData);
            }else {
                trackerData = new TrackerData();
                trackerData.setValue(1.0);
                trackerData.setDataName("Social");
                trackerRepository.AddTrackedData(trackerData);
            }
        }catch (Error error){

        }

        if(mode== 0){
            BadgeAward();
        }


        SocialFragment socialFragment = new SocialFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, socialFragment).commit();
    }

    public void BadgeAward(){
        BadgeRepository badgeRepository = new BadgeRepository(getContext());
        Badge badge = badgeRepository.GetBadgeFromTitle("A friend in me");
        TrackerRepository trackerRepository = new TrackerRepository(getContext());

        if (badge == null){
            badge = new Badge("A friend in me", "You have helped someone for the first time","bronze","high_five");
            badgeRepository.AddBadge(badge);
            Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: A friend in me", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        if(trackerRepository.GetSingleDataFromSection("Social")!= null){
            TrackerData trackerData = trackerRepository.GetSingleDataFromSection("Social");

            if (trackerData.getValue() >= 5 && badgeRepository.GetBadgeFromTitle("Community Builder") == null){
                badge = new Badge("Community Builder", "You have helped more than 3 people!","silver","high_five");
                badgeRepository.AddBadge(badge);
                Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: Community Builder!", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

            if (trackerData.getValue() >= 10 && badgeRepository.GetBadgeFromTitle("Community Leader") == null){
                badge = new Badge("Community Leader", "You have helped more than 10 people!","gold","high_five");
                badgeRepository.AddBadge(badge);
                Snackbar snackbar = Snackbar.make(getView(),"New Badge Unlocked: Community Leader!", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

        }

    }
}