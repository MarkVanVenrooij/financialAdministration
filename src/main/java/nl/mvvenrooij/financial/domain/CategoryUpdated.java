package nl.mvvenrooij.financial.domain;

import nl.mvvenrooij.financial.domainevents.DomainEvent;

public class CategoryUpdated extends DomainEvent<Category> {

    public CategoryUpdated(Category category) {
        super(category);
    }
}
