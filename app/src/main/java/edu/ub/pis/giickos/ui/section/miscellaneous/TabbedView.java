package edu.ub.pis.giickos.ui.section.miscellaneous;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.TabbedViewAdapter;
import edu.ub.pis.giickos.ui.section.miscellaneous.settings.SettingsFragment;
import edu.ub.pis.giickos.ui.section.miscellaneous.statistics.StatisticsFragment;
import edu.ub.pis.giickos.ui.section.miscellaneous.team.TeamsFragment;

// Tabbed view for the miscellaneous section.
public class TabbedView extends edu.ub.pis.giickos.ui.generic.TabbedView {
    @Override
    protected TabbedViewAdapter getAdapter() {
        List<TabbedViewAdapter.TabbedViewTab> tabs = new ArrayList<>();

        tabs.add(new TabbedViewAdapter.TabbedViewTab(getString(R.string.miscellaneous_tab_statistics)) {
            @Override
            public Fragment createFragment() {
                return StatisticsFragment.newInstance();
            }
        });
        tabs.add(new TabbedViewAdapter.TabbedViewTab(getString(R.string.miscellaneous_tab_teams)) {
            @Override
            public Fragment createFragment() {
                return TeamsFragment.newInstance();
            }
        });
        tabs.add(new TabbedViewAdapter.TabbedViewTab(getString(R.string.miscellaneous_tab_settings)) {
            @Override
            public Fragment createFragment() {
                return SettingsFragment.newInstance();
            }
        });

        return new TabbedViewAdapter(this, tabs);
    }
}
