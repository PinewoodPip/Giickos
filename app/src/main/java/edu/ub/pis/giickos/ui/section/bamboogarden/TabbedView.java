package edu.ub.pis.giickos.ui.section.bamboogarden;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.TabbedViewAdapter;
import edu.ub.pis.giickos.ui.section.bamboogarden.garden.GardenFragment;
import edu.ub.pis.giickos.ui.section.bamboogarden.storage.StorageFragment;
import edu.ub.pis.giickos.ui.section.miscellaneous.settings.SettingsFragment;
import edu.ub.pis.giickos.ui.section.miscellaneous.statistics.StatisticsFragment;
import edu.ub.pis.giickos.ui.section.miscellaneous.team.TeamsFragment;

// Tabbed view for the bamboo garden section.
public class TabbedView extends edu.ub.pis.giickos.ui.main.TabbedView {
    @Override
    protected TabbedViewAdapter getAdapter() {
        List<TabbedViewAdapter.TabbedViewTab> tabs = new ArrayList<>();

        tabs.add(new TabbedViewAdapter.TabbedViewTab(getString(R.string.bamboo_garden_tab)) {
            @Override
            public Fragment createFragment() {
                return GardenFragment.newInstance();
            }
        });
        tabs.add(new TabbedViewAdapter.TabbedViewTab(getString(R.string.bamboo_storage_tab)) {
            @Override
            public Fragment createFragment() {
                return StorageFragment.newInstance();
            }
        });

        return new TabbedViewAdapter(this, tabs);
    }
}
