package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;

import java.util.function.Function;

public class BaseRule implements CategoryRule {
    private final Category categoryToAssign;
    private final Function<Transaction, Boolean> functionToApply;

    public BaseRule(final Category categoryToAssign, final Function<Transaction, Boolean> functionToApply) {
        this.categoryToAssign = categoryToAssign;
        this.functionToApply = functionToApply;
    }

    @Override
    public boolean categorize(final Transaction transaction) {
        if (functionToApply.apply(transaction)) {
            categoryToAssign.addTransactions(transaction);
            return true;
        }
        return false;
    }
}
