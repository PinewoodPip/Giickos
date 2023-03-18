package edu.ub.pis.giickos.ui.observer;

import edu.ub.pis.giickos.GiickosFragment;

import java.util.*;

// Observer implementation that allows observers to subscribe to various events from a single
// observable, defined by enum.
// Observers only receive update() calls for the event types they are subscribed to.
public class Observable<T extends Enum<T>> extends GiickosFragment {
    private HashMap<T, List<edu.ub.pis.giickos.ui.observer.Observer>> observers;

    public Observable() {
        this.observers = new HashMap<>();
    }

    public void subscribe(T eventType, edu.ub.pis.giickos.ui.observer.Observer observer) {
        // Initialize list of subscribers
        if (!observers.containsKey(eventType)) {
            observers.put(eventType, new LinkedList<>());
        }

        getObservers(eventType).add(observer);
    }

    public void unsubscribe(T eventType, edu.ub.pis.giickos.ui.observer.Observer observer) {
        getObservers(eventType).remove(observer);
    }

    protected void notifyObservers(T eventType, ObservableEvent eventData) {
        for (edu.ub.pis.giickos.ui.observer.Observer observer : getObservers(eventType)) {
            observer.update(eventData);
        }
    }

    private List<edu.ub.pis.giickos.ui.observer.Observer> getObservers(T eventType) {
        List<Observer> observers = this.observers.get(eventType);

        // Return an empty list instead of null so the client does not need to null-check in case there are no subscribers of this event type.
        if (observers == null) {
            observers = new LinkedList<>();
        }

        return observers;
    }
}
