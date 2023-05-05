package nl.mvvenrooij.financial.domainevents;

import java.util.HashSet;
import java.util.Set;

public final class EventBus<T extends DomainEvent<?>>{

    private final Set<EventListener<T>> listeners = new HashSet<>();

    public void registerListener(final EventListener<T> listener) {
        if (listeners.stream().noneMatch((l) -> l.getClass().equals(listener.getClass()))) {
            listeners.add(listener);
        }
    }

    public void publish(final T event) {
        for (EventListener<T> listener : listeners) {
            listener.handleEvent(event);
        }
    }
}
