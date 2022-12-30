package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;

public class DescriptionCategorizationRule extends BaseCategorizationRule {

    public DescriptionCategorizationRule(final Category category, final String descriptionToMatch) {
        super(category, (transaction) -> transaction.description().contains(descriptionToMatch));
    }
}
