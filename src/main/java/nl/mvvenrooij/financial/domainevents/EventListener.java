package nl.mvvenrooij.financial.domainevents;

public interface EventListener<T extends DomainEvent<?>> {
    default void registerListener() {
        EventBus.getInstance().registerListener((EventListener<DomainEvent<?>>) this);
    }

    void  handleEvent(final T event);
}
