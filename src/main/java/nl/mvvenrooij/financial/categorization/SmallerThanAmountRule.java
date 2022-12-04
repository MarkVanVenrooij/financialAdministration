package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import org.javamoney.moneta.Money;

public class SmallerThanAmountRule extends AmountRule {

    public SmallerThanAmountRule(final Category category, final Money amountToCompareTo) {
        super(category, amountToCompareTo::isGreaterThan);
    }
}
