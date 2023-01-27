package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;

public class CounterPartyCategorizationRule extends BaseCategorizationRule {

    private final String counterParty;

    public CounterPartyCategorizationRule(Category category, String counterParty) {
        super(category, (transaction) -> transaction.counterParty().contains(counterParty));
        this.counterParty = counterParty;
    }

    @Override
    public Object constructorValue() {
        return counterParty;
    }
}
