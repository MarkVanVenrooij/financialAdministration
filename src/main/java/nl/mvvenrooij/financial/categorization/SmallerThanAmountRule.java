package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.javamoney.moneta.Money;

public class SmallerThanAmountRule implements CategoryRule{
    private final Category category;
    private final Money amountToCompareTo;

    public SmallerThanAmountRule(final Category category, final Money amountToCompareTo) {

        this.category = category;
        this.amountToCompareTo = amountToCompareTo;
    }

    @Override
    public boolean categorize(final Transaction transaction) {
        if (amountToCompareTo.isGreaterThan(transaction.amount())) {
            category.addTransactions(transaction);
            return true;
        }
        return false;
    }
}
