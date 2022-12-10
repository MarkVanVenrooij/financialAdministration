package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import org.javamoney.moneta.Money;

public class SmallerThanAmountRule extends BaseRule {

    public SmallerThanAmountRule(final Category category, final Money amountToCompareTo) {
        super(category, (transaction -> amountToCompareTo.isGreaterThan(transaction.amount().abs())));
    }
}
