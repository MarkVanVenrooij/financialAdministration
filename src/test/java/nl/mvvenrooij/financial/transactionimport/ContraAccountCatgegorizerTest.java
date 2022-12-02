package nl.mvvenrooij.financial.transactionimport;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;

public class ContraAccountCatgegorizerTest {

    private final Transaction transaction =  new Transaction("account", null, "contraAccount", null, null);
    private final Category category = new Category("SomeCat");

    @Test
    public void transactionWithMatchingContraAccount_isAddedToCategory() {
        final Categorizer contraAccountCatgegorizer = new ContraAccountCatgegorizer(category, "contraAccount");
        contraAccountCatgegorizer.categorize(transaction);
        assertThat(category.transactions(), hasItems(transaction));
    }

    @Test
    public void transactionWithoutMatchingContraAccount_isNotAddedToCategory() {
        final Categorizer contraAccountCatgegorizer = new ContraAccountCatgegorizer(category, "contraAccount2");
        contraAccountCatgegorizer.categorize(transaction);
        assertThat(category.transactions(), not(hasItems(transaction)));
    }
}
