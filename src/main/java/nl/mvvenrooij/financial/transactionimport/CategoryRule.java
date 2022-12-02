package nl.mvvenrooij.financial.transactionimport;

import nl.mvvenrooij.financial.domain.Transaction;

public interface CategoryRule {
    boolean categorize(final Transaction transaction);
}
