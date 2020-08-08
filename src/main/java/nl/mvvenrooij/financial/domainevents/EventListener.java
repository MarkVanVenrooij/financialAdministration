package nl.mvvenrooij.financial.domainevents;

public interface EventListener<T extends DomainEvent<?>> {
    default void registerListener() {
        EventBus.registerListener(this);
    }

    void  handleEvent(final T event);
}
