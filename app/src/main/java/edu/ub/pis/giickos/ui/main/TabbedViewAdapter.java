package edu.ub.pis.giickos.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

// Adapter class for TabbedView.
public class TabbedViewAdapter extends FragmentStateAdapter {
    protected List<TabbedViewTab> tabs;

    public TabbedViewAdapter(Fragment fragment, List<TabbedViewTab> tabs) {
        super(fragment);
        this.tabs = tabs;
    }

    public String getTabHeader(int position) {
        return tabs.get(position).getHeader();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return tabs.get(position).createFragment();
    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }

    // Represents a tab and provides its header and Fragment constructor.
    public abstract static class TabbedViewTab {
        private String header;

        public TabbedViewTab(String header) {
            this.header = header;
        }

        public String getHeader() {
            return header;
        }

        public abstract Fragment createFragment();
    }
}
