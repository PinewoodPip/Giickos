package edu.ub.pis.giickos.ui.main.sectionbar;

import edu.ub.pis.giickos.ui.observer.ObservableEvent;
import edu.ub.pis.giickos.ui.observer.Observable;
import edu.ub.pis.giickos.ui.section.Section;

public class SectionBarItemEvent extends ObservableEvent<SectionBarEvents> {
    private Section.TYPE sectionID;

    public SectionBarItemEvent(Observable eventSource, Section.TYPE sectionID) {
        super(eventSource);
        this.sectionID = sectionID;
    }

    @Override
    public SectionBarEvents getEventType() {
        return SectionBarEvents.SECTION_PRESSED;
    }

    public Section.TYPE getSectionID() {
        return sectionID;
    }
}
