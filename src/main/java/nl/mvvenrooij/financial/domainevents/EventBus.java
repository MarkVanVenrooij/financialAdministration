package nl.mvvenrooij.financial.domainevents;

import java.util.HashSet;
import java.util.Set;

public final class EventBus<T extends DomainEvent<?>>{

    private static final EventBus INSTANCE = new EventBus<>();

    private final Set<EventListener<T>> listeners = new HashSet<>();

    private EventBus() {
    }

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

    public static <T extends DomainEvent<?>> EventBus<T> getInstance() {
        return (EventBus<T>) INSTANCE;
    }
}
