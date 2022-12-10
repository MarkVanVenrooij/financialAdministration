package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CounterPartyRuleTest {
    @Test
    public void transactionWithExactMatchingDescription_shouldBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, "some special my shop", null, null);
        Category category = new Category("someCategory");

        CounterPartyCatgegoryRule descriptionRule = new CounterPartyCatgegoryRule(category, "some special my shop");
        assertTrue(descriptionRule.categorize(toCategorize));
        assertThat(category.transactions(), hasItems(toCategorize));
    }

    @Test
    public void transactionWithDifferentDescription_shouldNotBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, "another shop", null, null);
        Category category = new Category("someCategory");

        CounterPartyCatgegoryRule descriptionRule = new CounterPartyCatgegoryRule(category, "some special my shop");
        assertFalse(descriptionRule.categorize(toCategorize));
        assertThat(category.transactions(), not(hasItems(toCategorize)));
    }

    @Test
    public void transactionWithPartialyMatchedDescrition_shouldBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, "some special my shop", null, null);
        Category category = new Category("someCategory");

        CounterPartyCatgegoryRule descriptionRule = new CounterPartyCatgegoryRule(category, "special");
        assertTrue(descriptionRule.categorize(toCategorize));
        assertThat(category.transactions(), hasItems(toCategorize));
    }
}
