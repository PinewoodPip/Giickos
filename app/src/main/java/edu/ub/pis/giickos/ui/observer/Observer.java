package edu.ub.pis.giickos.ui.observer;

// Observer interface. T should be the enum containing the different event IDs that the observable may fire.
public interface Observer<T extends Enum<T>> {
    void update(ObservableEvent<T> eventData);
}
