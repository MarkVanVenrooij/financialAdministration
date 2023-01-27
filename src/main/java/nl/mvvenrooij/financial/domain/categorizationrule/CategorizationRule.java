package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;

public interface CategorizationRule {

    boolean matches(final Transaction transaction);

    Category category();

    Object constructorValue();
}
