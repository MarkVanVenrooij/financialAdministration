package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CounterPartyRuleTest {
    @Test
    public void transactionWithExactMatchingDescription_shouldBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, "some special my shop", null, null);
        Category category = new Category("someCategory");

        CounterPartyCategorizationRule descriptionRule = new CounterPartyCategorizationRule(category, "some special my shop");
        assertTrue(descriptionRule.matches(toCategorize));
    }

    @Test
    public void transactionWithDifferentDescription_shouldNotBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, "another shop", null, null);
        Category category = new Category("someCategory");

        CounterPartyCategorizationRule descriptionRule = new CounterPartyCategorizationRule(category, "some special my shop");
        assertFalse(descriptionRule.matches(toCategorize));
    }

    @Test
    public void transactionWithPartialyMatchedDescrition_shouldBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, "some special my shop", null, null);
        Category category = new Category("someCategory");

        CounterPartyCategorizationRule descriptionRule = new CounterPartyCategorizationRule(category, "special");
        assertTrue(descriptionRule.matches(toCategorize));
    }
}
