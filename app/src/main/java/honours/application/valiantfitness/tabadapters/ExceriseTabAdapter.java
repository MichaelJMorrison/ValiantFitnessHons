package honours.application.valiantfitness.tabadapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import honours.application.valiantfitness.*;
public class ExceriseTabAdapter extends FragmentStateAdapter {
    private Context context;

    public ExceriseTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ExceriseTabAdapter(@NonNull FragmentActivity fragmentActivity,Context context) {
        super(fragmentActivity);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int).
        Fragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        // The object is just an integer.

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
