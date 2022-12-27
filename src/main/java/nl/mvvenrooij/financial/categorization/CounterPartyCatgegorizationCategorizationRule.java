package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;

public class CounterPartyCatgegorizationCategorizationRule extends BaseCategorizationRule {

    public CounterPartyCatgegorizationCategorizationRule(final Category category, final String counterParty) {
        super(category, (transaction) -> transaction.counterParty().contains(counterParty));
    }
}