package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;

public class UncategorizedCategorizationCategorizationRule extends BaseCategorizationRule {

    public UncategorizedCategorizationCategorizationRule(final Category uncategorizedCategory) {
        super(uncategorizedCategory, (transaction -> true));
    }
}
