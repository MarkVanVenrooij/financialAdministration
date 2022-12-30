package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContraAccountCatgegorizerTest {

    private final Transaction transaction = new Transaction("account", null, "contraAccount", null, null, null);
    private final Category category = new Category("SomeCat");

    @Test
    public void transactionWithMatchingContraAccount_matchesCategory() {
        final CategorizationRule contraAccountCatgegorizer = new ContraAccountCategorizationRule(category, "contraAccount");
        assertTrue(contraAccountCatgegorizer.matches(transaction));
    }

    @Test
    public void transactionWithoutMatchingContraAccount_isNotAddedToCategory() {
        final CategorizationRule contraAccountCatgegorizer = new ContraAccountCategorizationRule(category, "contraAccount2");
        contraAccountCatgegorizer.matches(transaction);
    }
}
