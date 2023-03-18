package edu.ub.pis.giickos.ui.observer;

// Base class for events fired by Observables.
// Holds the object source and the event type (as enum constant)
public abstract class ObservableEvent<T extends Enum<T>> {
    private Observable source; // The Observable object the event originates from.

    public ObservableEvent(Observable eventSource) {
        this.source = eventSource;
    }

    public Observable getSource() {
        return source;
    }

    public abstract T getEventType();
}
