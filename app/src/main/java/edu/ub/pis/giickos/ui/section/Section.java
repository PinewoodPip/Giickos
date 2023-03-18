package edu.ub.pis.giickos.ui.section;

import edu.ub.pis.giickos.GiickosFragment;

public abstract class Section extends GiickosFragment {
    public enum TYPE { // TODO move this to model / viewmodel
        TASK_EXPLORER,
        TASK_CREATOR,
    }

    public abstract TYPE getType();
}
