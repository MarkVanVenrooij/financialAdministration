package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import org.javamoney.moneta.Money;

public class SmallerThanAmountCategorizationRule extends BaseCategorizationRule {

    private final Money amountToCompareTo;

    public SmallerThanAmountCategorizationRule(final Category category, final String amountToCompareTo) {
        this(category, Money.parse(amountToCompareTo));
    }

    public SmallerThanAmountCategorizationRule(final Category category, final Money amountToCompareTo) {
        super(category, (transaction -> amountToCompareTo.isGreaterThan(transaction.amount().abs())));
        this.amountToCompareTo = amountToCompareTo;
    }

    @Override
    public Object constructorValue() {
        return amountToCompareTo;
    }
}
