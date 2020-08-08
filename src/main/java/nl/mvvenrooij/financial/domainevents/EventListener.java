package nl.mvvenrooij.financial.domainevents;

public interface EventListener<T> {
    default void registerListener() {
        EventBus.registerListener(this);
    }

    void handleEvent(final DomainEvent<T> event);
}
