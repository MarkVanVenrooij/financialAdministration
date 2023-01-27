package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import org.javamoney.moneta.Money;

public class LargerThanAmountCategorizationRule extends BaseCategorizationRule {
    private final Money amountToCompareTo;

    public LargerThanAmountCategorizationRule(final Category category, final Money amountToCompareTo) {
        super(category, (transaction -> amountToCompareTo.isLessThan(transaction.amount())));
        this.amountToCompareTo = amountToCompareTo;
    }

    @Override
    public Object constructorValue() {
        return amountToCompareTo;
    }
}
