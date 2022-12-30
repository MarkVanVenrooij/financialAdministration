package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DescriptionCategorizationRuleTest {
    @Test
    public void transactionWithExactMatchingDescription_shouldBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, null, null, "description");
        Category category = new Category("someCategory");

        DescriptionCategorizationRule descriptionCategorizationRule = new DescriptionCategorizationRule(category, "description");
        assertTrue(descriptionCategorizationRule.matches(toCategorize));
    }

    @Test
    public void transactionWithDifferentDescription_shouldNotBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, null, null, "nothing related");
        Category category = new Category("someCategory");

        DescriptionCategorizationRule descriptionCategorizationRule = new DescriptionCategorizationRule(category, "description");
        assertFalse(descriptionCategorizationRule.matches(toCategorize));
    }

    @Test
    public void transactionWithPartialyMatchedDescrition_shouldBeMatchedWithCategory() {
        Transaction toCategorize = new Transaction(null, null, null, null, null, "some description of something");
        Category category = new Category("someCategory");

        DescriptionCategorizationRule descriptionCategorizationRule = new DescriptionCategorizationRule(category, "description");
        assertTrue(descriptionCategorizationRule.matches(toCategorize));
    }
}
