package nl.mvvenrooij.financial.domainevents;

public abstract class DomainEvent<T> {
    private final T value;

    public DomainEvent(final T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }
}
