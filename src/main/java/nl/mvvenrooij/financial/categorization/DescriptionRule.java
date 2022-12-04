package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;

public class DescriptionRule implements CategoryRule {
    private final Category category;
    private final String descriptionToMatch;

    public DescriptionRule(final Category category, final String descriptionToMatch) {
        this.category = category;
        this.descriptionToMatch = descriptionToMatch;
    }

    @Override
    public boolean categorize(Transaction transaction) {
        if (transaction.description().contains(descriptionToMatch)) {
            category.addTransactions(transaction);
            return true;
        }
        return false;
    }
}
