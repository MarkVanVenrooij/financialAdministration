package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;


public class CategorizationRulesTest {
    private final Transaction transaction =  new Transaction("account", null, "contraAccount", null, null);
    private final Transaction transaction2 =  new Transaction("account", null, "contraAccount2", null, null);
    private final Category firstCategory = new Category("firstCategory");
    private final Category secondCategory = new Category("secondCategory");
    private final Category uncategorized = new Category("UNCATEGORIZED");

    @Test
    public void transactionAdheresToNoRules_IsCategorizedAsUncategorized() {
        CategorizationRules categorizationRules = new CategorizationRules(uncategorized);
        categorizationRules.apply(Collections.singletonList(transaction));
        assertThat(uncategorized.transactions(), hasItems(transaction));
    }

    @Test
    public void transactionAdheresToFirstRule_IsCategorizedFirstRule() {
        CategorizationRules categorizationRules = new CategorizationRules(uncategorized);
        categorizationRules.add(new ContraAccountCatgegoryRule(firstCategory, "contraAccount"));

        categorizationRules.apply(Collections.singletonList(transaction));

        assertThat(firstCategory.transactions(), hasItems(transaction));
        assertThat(uncategorized.transactions(), not(hasItems(transaction)));
    }

    @Test
    public void firstTransactionFirstCategory_SecondtransactionSecondCategory() {
        CategorizationRules categorizationRules = new CategorizationRules(uncategorized);
        categorizationRules.add(new ContraAccountCatgegoryRule(firstCategory, "contraAccount"));
        categorizationRules.add(new ContraAccountCatgegoryRule(secondCategory, "contraAccount2"));

        categorizationRules.apply(Arrays.asList(transaction, transaction2));

        assertThat(firstCategory.transactions(), hasItems(transaction));
        assertThat(secondCategory.transactions(), hasItems(transaction2));
        assertThat(uncategorized.transactions(), not(hasItems(transaction)));
    }

    @Test
    public void transactionAdheresToSecondRule_IsCategorizedBySecondRule() {
        CategorizationRules categorizationRules = new CategorizationRules(uncategorized);
        categorizationRules.add(new ContraAccountCatgegoryRule(firstCategory, "contraAccount2"));
        categorizationRules.add(new ContraAccountCatgegoryRule(secondCategory, "contraAccount"));

        categorizationRules.apply(Collections.singletonList(transaction));

        assertThat(firstCategory.transactions(), not(hasItems(transaction)));
        assertThat(secondCategory.transactions(), hasItems(transaction));
        assertThat(uncategorized.transactions(), not(hasItems(transaction)));
    }
}
