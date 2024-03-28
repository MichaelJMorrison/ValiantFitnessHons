package honours.application.valiantfitness;

import static androidx.core.content.ContextCompat.*;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.se.omapi.Channel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import honours.application.valiantfitness.socialdata.WifiBroadcast;


public class SocialHelpFragment extends Fragment {

    public static final String ARG_MODE = "mode";

    private static final String TAG = "SocialHelpFragment";

    //https://developer.android.com/develop/connectivity/wifi/wifi-direct#receiver For all the wifi related work for this
    private final IntentFilter intentFilter = new IntentFilter();

    WifiP2pManager manager;

    WifiP2pManager.Channel channel;

    private TextView txtSearchInfo;

    private Button btnSocialClose;

    private WifiBroadcast broadcastReceiver;

    private int mode;
    public SocialHelpFragment() {
        // Required empty public constructor
    }


    public static SocialHelpFragment newInstance(int mode) {
        SocialHelpFragment fragment = new SocialHelpFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MODE, mode);
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
            Log.d(TAG, Integer.toString(mode));
        }
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        MainActivity activity = (MainActivity) getContext();

//https://stackoverflow.com/questions/19102948/initialize-method-to-register-with-wifip2p-framework-in-fragment
        manager = (WifiP2pManager) getActivity().getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(getContext(),getContext().getMainLooper(),null);
        broadcastReceiver = new WifiBroadcast(manager,channel,activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        txtSearchInfo = view.findViewById(R.id.txtSearchInfo);
        btnSocialClose = view.findViewById(R.id.btnSocialClose);

        if (mode == 0){
            txtSearchInfo.setText("PLEASE WAIT WHILE WE FIND PEOPLE TO HELP");
        }else{
            txtSearchInfo.setText("PLEASE WAIT WHILE WE FIND SOMEONE TO HELP YOU");
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
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}