package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;

public class UncategorizedCategorizationCategorizationRule extends BaseCategorizationRule {

    public UncategorizedCategorizationCategorizationRule(final Category uncategorizedCategory) {
        super(uncategorizedCategory, (transaction -> true));
    }
}
