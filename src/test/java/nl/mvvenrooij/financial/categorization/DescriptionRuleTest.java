package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DescriptionRuleTest {
    @Test
    public void transactionWithExactMatchingDescription_shouldBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, null, null, "description");
        Category category = new Category("someCategory");

        DescriptionRule descriptionRule = new DescriptionRule(category, "description");
        assertTrue(descriptionRule.categorize(toCategorize));
        assertThat(category.transactions(), hasItems(toCategorize));
    }

    @Test
    public void transactionWithDifferentDescription_shouldNotBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, null, null, "nothing related");
        Category category = new Category("someCategory");

        DescriptionRule descriptionRule = new DescriptionRule(category, "description");
        assertFalse(descriptionRule.categorize(toCategorize));
        assertThat(category.transactions(), not(hasItems(toCategorize)));
    }

    @Test
    public void transactionWithPartialyMatchedDescrition_shouldBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, null, null, "some description of something");
        Category category = new Category("someCategory");

        DescriptionRule descriptionRule = new DescriptionRule(category, "description");
        assertTrue(descriptionRule.categorize(toCategorize));
        assertThat(category.transactions(), hasItems(toCategorize));
    }
}
