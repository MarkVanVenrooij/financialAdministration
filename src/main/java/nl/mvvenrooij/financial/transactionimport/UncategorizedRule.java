package nl.mvvenrooij.financial.transactionimport;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;

public class UncategorizedRule implements CategoryRule {
    private final Category uncategorizedCategory;

    public UncategorizedRule(final Category uncategorizedCategory) {
        this.uncategorizedCategory = uncategorizedCategory;
    }

    @Override
    public boolean categorize(final Transaction transaction) {
        uncategorizedCategory.addTransactions(transaction);
        return true;
    }
}
