package nl.mvvenrooij.financial.domainevents;

import java.util.HashSet;
import java.util.Set;

public final class EventBus {

    private static final Set<EventListener> listeners = new HashSet<>();

    private EventBus() {
    }

    public static void registerListener(final EventListener listener) {
        if (listeners.stream().noneMatch((l) -> l.getClass().equals(listener.getClass()))) {
            listeners.add(listener);
        }
    }

    public static void publish(final DomainEvent event) {
        for (EventListener listener : listeners) {
            listener.handleEvent(event);
        }
    }
}
