package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Transaction;

public interface CategoryRule {
    boolean categorize(final Transaction transaction);
}
