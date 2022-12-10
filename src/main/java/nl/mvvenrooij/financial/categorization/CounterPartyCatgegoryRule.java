package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;

public class CounterPartyCatgegoryRule extends BaseRule {

    public CounterPartyCatgegoryRule(final Category category, final String counterParty) {
        super(category, (transaction) -> transaction.counterParty().contains(counterParty));
    }
}
