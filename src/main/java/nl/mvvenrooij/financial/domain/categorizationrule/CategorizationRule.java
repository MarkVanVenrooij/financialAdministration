package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;

public interface CategorizationRule {
    boolean categorize(final Transaction transaction);

    boolean matches(final Transaction transaction);

    Category category();
}
