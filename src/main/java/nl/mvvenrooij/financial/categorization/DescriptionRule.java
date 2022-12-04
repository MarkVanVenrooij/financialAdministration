package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;

public class DescriptionRule extends BaseRule {

    public DescriptionRule(final Category category, final String descriptionToMatch) {
        super(category, (transaction) -> transaction.description().contains(descriptionToMatch));
    }
}
