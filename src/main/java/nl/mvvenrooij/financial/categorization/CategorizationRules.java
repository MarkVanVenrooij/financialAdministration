package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CategorizationRules {

    private final List<CategoryRule> categorizationRules = new ArrayList<>();
    private final CategoryRule uncategorizedRule;

    public CategorizationRules(final Category uncategorizedCategory) {
        uncategorizedRule = new UncategorizedRule(uncategorizedCategory);
    }

    public void add(final ContraAccountCatgegoryRule contraAccountCatgegoryRule) {
        categorizationRules.add(contraAccountCatgegoryRule);
    }

    public void apply(final List<Transaction> transactions) {
        categorizationRules.add(uncategorizedRule);
        for (Transaction transaction : transactions) {
            categorizeTransaction(transaction);
        }

    }

    private void categorizeTransaction(Transaction transaction) {
        for (CategoryRule categorizationRules : categorizationRules) {
            boolean categorized = categorizationRules.categorize(transaction);
            if (categorized) {
                return;
            }
        }
    }
}
