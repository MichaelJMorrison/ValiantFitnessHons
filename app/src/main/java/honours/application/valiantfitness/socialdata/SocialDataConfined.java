package honours.application.valiantfitness.socialdata;

import android.net.wifi.p2p.WifiP2pDevice;

import java.util.List;

public class SocialDataConfined {

    private String  username;
    private String description;

    private WifiP2pDevice device;

    public SocialDataConfined(String username, String description, WifiP2pDevice device) {
        this.username = username;
        this.description = description;
        this.device = device;
    }

    public WifiP2pDevice getDevice() {
        return device;
    }

    public void setDevice(WifiP2pDevice device) {
        this.device = device;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
