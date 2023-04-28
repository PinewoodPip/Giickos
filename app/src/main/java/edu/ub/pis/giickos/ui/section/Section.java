package edu.ub.pis.giickos.ui.section;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.ui.activities.main.MainViewModel;

public abstract class Section extends GiickosFragment {

    public abstract MainViewModel.SECTION_TYPE getType();
}
