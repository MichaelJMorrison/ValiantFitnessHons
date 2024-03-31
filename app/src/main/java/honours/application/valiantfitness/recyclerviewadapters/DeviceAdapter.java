package honours.application.valiantfitness.recyclerviewadapters;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.SocialHelpFragment;
import honours.application.valiantfitness.badge.Badge;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private Context context;

    private List<WifiP2pDevice> deviceList;
    private static final String TAG = "DeviceAdapter";

    private SocialHelpFragment socialHelpFragment;

    public DeviceAdapter(Context context, List<WifiP2pDevice> deviceList, SocialHelpFragment socialHelpFragment) {
        super();
        this.context = context;
        this.deviceList = deviceList;
        this.socialHelpFragment = socialHelpFragment;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<WifiP2pDevice> getDeviceList() {
        return this.deviceList;
    }

    public void setBadges(List<WifiP2pDevice> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public int getItemCount() {
        return this.deviceList.size();
    }

    @NonNull
    @Override
    public DeviceAdapter.DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.socialhelpitem, parent, false);


        DeviceAdapter.DeviceViewHolder viewHolder = new DeviceAdapter.DeviceViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.DeviceViewHolder holder, int position) {
        WifiP2pDevice device = deviceList.get(position);
        holder.setDevice(device);
        TextView textView = holder.itemView.findViewById(R.id.txtNameItem);
        textView.setText(device.deviceName);
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private DeviceAdapter adapter;

        private Button btnAccept;

        private WifiP2pDevice device;

        public DeviceViewHolder(@NonNull View itemView, DeviceAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            btnAccept = itemView.findViewById(R.id.btnAccept);

            btnAccept.setOnClickListener(this);
        }


        public WifiP2pDevice getDevice() {
            return device;
        }

        public void setDevice(WifiP2pDevice device) {
            this.device = device;
        }

        @Override
        public void onClick(View view) {

            WifiP2pConfig wifiP2pConfig = new WifiP2pConfig();
            wifiP2pConfig.deviceAddress = device.deviceAddress;
            SocialHelpFragment socialHelpFragment1 = this.adapter.socialHelpFragment;

            if (ActivityCompat.checkSelfPermission(socialHelpFragment1.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(socialHelpFragment1.getContext(), Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {


            }
            socialHelpFragment1.getManager().connect(socialHelpFragment1.getChannel(), wifiP2pConfig, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Connection Successful");



                                    }

                @Override
                public void onFailure(int i) {

                }
            });

        }
    }

}
