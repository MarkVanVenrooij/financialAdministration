package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CategorizationRules {

    private final List<CategorizationRule> categorizationRules = new ArrayList<>();
    private final CategorizationRule uncategorizedRule;

    public CategorizationRules(final Category uncategorizedCategory) {
        uncategorizedRule = new UncategorizedCategorizationCategorizationRule(uncategorizedCategory);
    }

    public void add(final CategorizationRule categorizationRule) {
        categorizationRules.add(categorizationRule);
    }

    public void apply(final List<Transaction> transactions) {
        categorizationRules.add(uncategorizedRule);
        for (Transaction transaction : transactions) {
            categorizeTransaction(transaction);
        }

    }

    private void categorizeTransaction(Transaction transaction) {
        for (CategorizationRule categorizationRules : categorizationRules) {
            boolean categorized = categorizationRules.categorize(transaction);
            if (categorized) {
                return;
            }
        }
    }
}
