package nl.mvvenrooij.financial.domainevents;

public interface EventListener<T extends DomainEvent<?>> {
    default void registerListener(final EventBus<T> eventBus) {
        eventBus.registerListener(this);
    }

    void handleEvent(final T event);
}
