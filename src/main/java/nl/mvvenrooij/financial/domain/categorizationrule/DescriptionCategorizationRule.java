package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;

public class DescriptionCategorizationRule extends BaseCategorizationRule {

    private final String descriptionToMatch;

    public DescriptionCategorizationRule(final Category category, final String descriptionToMatch) {
        super(category, (transaction) -> transaction.description().contains(descriptionToMatch));
        this.descriptionToMatch = descriptionToMatch;
    }

    @Override
    public Object constructorValue() {
        return descriptionToMatch;
    }
}
