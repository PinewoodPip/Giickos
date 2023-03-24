package edu.ub.pis.giickos.ui.section;

import edu.ub.pis.giickos.GiickosFragment;

public abstract class Section extends GiickosFragment {
    public enum TYPE {
        TASK_EXPLORER,
        TASK_CREATOR,
        MISCELLANEOUS,
    }

    public abstract TYPE getType();
}
