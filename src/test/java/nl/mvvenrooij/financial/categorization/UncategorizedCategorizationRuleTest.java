package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UncategorizedCategorizationRuleTest {
    @Test
    public void allTransactionsAreAddedToCategory() {
        final Transaction toCategorize = new Transaction(null, null, null, null, null, null);
        final Category category = new Category("someCategory");
        final UncategorizedCategorizationCategorizationRule uncategorizedCategorizationRule = new UncategorizedCategorizationCategorizationRule(category);
        assertTrue(uncategorizedCategorizationRule.categorize(toCategorize));
        assertThat(category.transactions(), hasItems(toCategorize));
    }
}
