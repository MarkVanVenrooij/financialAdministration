package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import org.javamoney.moneta.Money;

public class LargerThanAmountCategorizationCategorizationRule extends BaseCategorizationRule {
    public LargerThanAmountCategorizationCategorizationRule(final Category category, final Money amountToCompareTo) {
        super(category, (transaction -> amountToCompareTo.isLessThan(transaction.amount())));
    }
}
