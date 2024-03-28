package honours.application.valiantfitness.socialdata;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

import honours.application.valiantfitness.MainActivity;

public class WifiBroadcast extends BroadcastReceiver {

private WifiP2pManager manager;

private WifiP2pManager.Channel channel;

private MainActivity activity;

    public WifiBroadcast(WifiP2pManager manager, WifiP2pManager.Channel channel,MainActivity activity) {
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){

        }

    }
}
