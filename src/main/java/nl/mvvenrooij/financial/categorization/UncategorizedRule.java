package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;

public class UncategorizedRule extends BaseRule {

    public UncategorizedRule(final Category uncategorizedCategory) {
        super(uncategorizedCategory, (transaction -> true));
    }
}
