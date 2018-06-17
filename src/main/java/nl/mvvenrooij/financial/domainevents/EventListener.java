package nl.mvvenrooij.financial.domainevents;

public interface EventListener {
    default void registerListener() {
        EventBus.registerListener(this);
    }

    void handleEvent(final DomainEvent event);
}
