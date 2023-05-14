package edu.ub.pis.giickos.ui.section.bamboogarden;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.TabbedViewAdapter;
import edu.ub.pis.giickos.ui.section.bamboogarden.garden.GardenFragment;
import edu.ub.pis.giickos.ui.section.bamboogarden.storage.StorageFragment;

// Tabbed view for the bamboo garden section.
public class TabbedView extends edu.ub.pis.giickos.ui.generic.TabbedView {
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
