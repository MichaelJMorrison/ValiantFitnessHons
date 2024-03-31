package honours.application.valiantfitness.recyclerviewadapters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import honours.application.valiantfitness.R;
import honours.application.valiantfitness.SocialHelpFragment;
import honours.application.valiantfitness.socialdata.SocialDataConfined;

public class DeviceAdapterV2 extends RecyclerView.Adapter<DeviceAdapterV2.DeviceViewHolder> {
    private Context context;

    private List<SocialDataConfined> deviceList;
    private static final String TAG = "DeviceAdapter";

    private SocialHelpFragment socialHelpFragment;

    public DeviceAdapterV2(Context context, List<SocialDataConfined> deviceList, SocialHelpFragment socialHelpFragment) {
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

    public List<SocialDataConfined> getDeviceList() {
        return this.deviceList;
    }

    public void setBadges(List<SocialDataConfined> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public int getItemCount() {
        return this.deviceList.size();
    }

    @NonNull
    @Override
    public DeviceAdapterV2.DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.socialhelpitem, parent, false);


        DeviceAdapterV2.DeviceViewHolder viewHolder = new DeviceAdapterV2.DeviceViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapterV2.DeviceViewHolder holder, int position) {
        SocialDataConfined device = deviceList.get(position);
        holder.setDevice(device);
        TextView textView = holder.itemView.findViewById(R.id.txtNameItem);
        textView.setText(device.getUsername());
        TextView textView1 = holder.itemView.findViewById(R.id.txtDescriptionItem);
        textView1.setText(device.getDescription());
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private DeviceAdapterV2 adapter;

        private Button btnAccept;

        private SocialDataConfined device;

        public DeviceViewHolder(@NonNull View itemView, DeviceAdapterV2 adapter) {
            super(itemView);
            this.adapter = adapter;
            btnAccept = itemView.findViewById(R.id.btnAccept);

            btnAccept.setOnClickListener(this);
        }


        public SocialDataConfined getDevice() {
            return device;
        }

        public void setDevice(SocialDataConfined device) {
            this.device = device;
        }

        @Override
        public void onClick(View view) {

            WifiP2pConfig wifiP2pConfig = new WifiP2pConfig();
            wifiP2pConfig.deviceAddress = device.getDevice().deviceAddress;
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
