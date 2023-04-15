package edu.ub.pis.giickos.model.observer;

// An event with no arguments other than type.
public class EmptyEvent<T extends Enum<T>> extends ObservableEvent {

    private T eventType;

    public EmptyEvent(Observable source, T eventType) {
        super(source);
        this.eventType = eventType;
    }
    @Override
    public T getEventType() {
        return eventType;
    }
}
