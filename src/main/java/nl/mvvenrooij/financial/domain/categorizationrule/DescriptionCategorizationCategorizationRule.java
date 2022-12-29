package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;

public class DescriptionCategorizationCategorizationRule extends BaseCategorizationRule {

    public DescriptionCategorizationCategorizationRule(final Category category, final String descriptionToMatch) {
        super(category, (transaction) -> transaction.description().contains(descriptionToMatch));
    }
}
