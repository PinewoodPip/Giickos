package edu.ub.pis.giickos.ui.section.timer;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.TabbedView;
import edu.ub.pis.giickos.ui.generic.TabbedViewAdapter;
import edu.ub.pis.giickos.ui.section.timer.detox.DetoxFragment;

// Tabbed view for the timer section.
public class TimerTabbedView extends TabbedView {
    @Override
    protected TabbedViewAdapter getAdapter() {
        List<TabbedViewAdapter.TabbedViewTab> tabs = new ArrayList<>();

        tabs.add(new TabbedViewAdapter.TabbedViewTab(getString(R.string.timer_tab_timer)) {
            @Override
            public Fragment createFragment() { return TimerFragment.newInstance(); }
        });
        tabs.add(new TabbedViewAdapter.TabbedViewTab(getString(R.string.timer_tab_detox)) {
            @Override
            public Fragment createFragment() { return DetoxFragment.newInstance(); }
        });


        return new TabbedViewAdapter(this, tabs);
    }
}
