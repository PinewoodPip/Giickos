package edu.ub.pis.giickos.ui.section;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.ui.main.MainViewModel;

public abstract class Section extends GiickosFragment {

    public abstract MainViewModel.TYPE getType();
}
