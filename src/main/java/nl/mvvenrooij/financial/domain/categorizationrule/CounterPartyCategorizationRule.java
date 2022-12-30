package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;

public class CounterPartyCategorizationRule extends BaseCategorizationRule {

    public CounterPartyCategorizationRule(final Category category, final String counterParty) {
        super(category, (transaction) -> transaction.counterParty().contains(counterParty));
    }
}
