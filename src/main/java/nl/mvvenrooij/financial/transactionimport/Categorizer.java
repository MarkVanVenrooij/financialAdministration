package nl.mvvenrooij.financial.transactionimport;

import nl.mvvenrooij.financial.domain.Transaction;

public interface Categorizer {
    void categorize(final Transaction transaction);
}
