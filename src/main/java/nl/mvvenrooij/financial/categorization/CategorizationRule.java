package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Transaction;

public interface CategorizationRule {
    boolean categorize(final Transaction transaction);
}