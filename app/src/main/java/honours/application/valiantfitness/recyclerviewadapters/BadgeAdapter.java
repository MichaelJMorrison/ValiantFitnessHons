package honours.application.valiantfitness.recyclerviewadapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import honours.application.valiantfitness.ProfileFragment;
import honours.application.valiantfitness.R;
import honours.application.valiantfitness.badge.Badge;
import honours.application.valiantfitness.exercisedata.ExerciseSetData;


public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>{

    private Context context;

    private List<Badge> badges;
    private static final String TAG = "BadgeAdapter";


    public BadgeAdapter(Context context, List<Badge> badges) {
        super();
        this.context = context;
        this.badges = badges;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }

    @Override
    public int getItemCount() {
        return this.badges.size();
    }

    @NonNull
    @Override
    public BadgeAdapter.BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.badgeitem, parent, false);


        BadgeAdapter.BadgeViewHolder viewHolder = new BadgeAdapter.BadgeViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull BadgeAdapter.BadgeViewHolder holder, int position) {
        Badge badge = badges.get(position);
        holder.setBadge(badge);
        ImageView imgBadgeTier = holder.itemView.findViewById(R.id.imgBadgeTier);
        ImageView imgBadgeIcon = holder.itemView.findViewById(R.id.imgBadgeIcon);
        int drawable = getContext().getResources().getIdentifier(badge.getIcon(),"drawable",getContext().getPackageName());
        int drawable1 = getContext().getResources().getIdentifier(badge.getSubIcon(),"drawable",getContext().getPackageName());
       Glide.with(holder.itemView).load(drawable).into(imgBadgeTier);
        Glide.with(holder.itemView).load(drawable1).into(imgBadgeIcon);
    }

    class BadgeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private BadgeAdapter adapter;



        private Badge badge;

        public BadgeViewHolder(@NonNull View itemView, BadgeAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            itemView.findViewById(R.id.btnBadge).setOnClickListener(this);

        }

        public Badge getBadge() {
            return badge;
        }

        public void setBadge(Badge badge) {
            this.badge = badge;
        }

        @Override
        public void onClick(View view) {

            AppCompatActivity activity = (AppCompatActivity)this.adapter.context;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(this.badge.getDescription());
            builder.setTitle(this.badge.getTitle());
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();



        }


   }}
