package edu.ub.pis.giickos.ui.generic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import edu.ub.pis.giickos.R;

// A container that allows swipe and tab-based navigation between various fragments.
// Uses the fragment_tabbed_view layout.
public abstract class TabbedView extends Fragment {

    public TabbedView() {
        // Required empty public constructor
    }

    // Returns the adapter used for the TabbedView
    protected abstract TabbedViewAdapter getAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Configures the pager and tab layout
    private void setup(ViewPager2 pager, TabLayout tabLayout) {
        TabbedViewAdapter adapter = getAdapter();

        // Set the adapter of the pager
        pager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, pager,
                (tab, position) -> tab.setText(adapter.getTabHeader(position))
        ).attach();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Find the TabLayout view
        TabLayout tabLayout = view.findViewById(this.getResources().getIdentifier("tab_layout", "id", requireContext().getPackageName()));

        // Customize the color of the TabLayout
        tabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.float_background));

        // Customize the color of the TabLayout's text
        tabLayout.setTabTextColors(ContextCompat.getColor(requireContext(), R.color.float_background_text_2), ContextCompat.getColor(requireContext(), R.color.float_background_text));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.float_background_dark));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabbed_view, container, false);

        setup(view.findViewById(R.id.pager), view.findViewById(R.id.tab_layout));

        return view;
    }
}