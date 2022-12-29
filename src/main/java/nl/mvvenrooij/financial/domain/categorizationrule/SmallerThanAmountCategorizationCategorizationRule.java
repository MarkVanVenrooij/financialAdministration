package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import org.javamoney.moneta.Money;

public class SmallerThanAmountCategorizationCategorizationRule extends BaseCategorizationRule {

    public SmallerThanAmountCategorizationCategorizationRule(final Category category, final Money amountToCompareTo) {
        super(category, (transaction -> amountToCompareTo.isGreaterThan(transaction.amount().abs())));
    }
}
