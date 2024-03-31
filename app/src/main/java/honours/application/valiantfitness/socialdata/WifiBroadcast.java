package honours.application.valiantfitness.socialdata;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import honours.application.valiantfitness.MainActivity;
import honours.application.valiantfitness.SocialHelpFragment;

public class WifiBroadcast extends BroadcastReceiver {

    private WifiP2pManager manager;

    private WifiP2pManager.Channel channel;

    private MainActivity activity;

    private SocialHelpFragment socialHelpFragment;

    private static final String TAG = "WifiBroadcast";

    private List<WifiP2pDevice> deviceList = new ArrayList<>();



    //REference Credit https://www.youtube.com/watch?v=ZHHypM1Rz9c&t

    public WifiBroadcast(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity) {
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    public WifiBroadcast(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity, SocialHelpFragment socialHelpFragment) {
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
        this.socialHelpFragment = socialHelpFragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            if (manager != null) {

                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activity, Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {


                }
                manager.requestPeers(channel, new WifiP2pManager.PeerListListener() {
                    @Override
                    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {

                        if (!wifiP2pDeviceList.equals(deviceList)) {
                            deviceList.clear();
                           deviceList.addAll(wifiP2pDeviceList.getDeviceList());
                           socialHelpFragment.DeviceListRender(deviceList);



                        }

                    }
                });
            }

        }

        if(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION.equals(action)){
            manager.discoverServices(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: ");
                }

                @Override
                public void onFailure(int i) {

                }
            });
        }


        if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if (manager != null) {

                NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

                if (networkInfo.isConnectedOrConnecting()) {
                    manager.requestConnectionInfo(socialHelpFragment.getChannel(), new WifiP2pManager.ConnectionInfoListener() {
                        @Override
                        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
                            Log.d(TAG, "Connection Successful");
                            socialHelpFragment.InformUser();

                            if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
                                //Person who needs help


                            } else if (wifiP2pInfo.groupFormed) {
                                //Helper

                            }

                            }
                        });
                }else{
                    Log.d(TAG, "Connection Failed");
                }

            }
        }

    }
}
