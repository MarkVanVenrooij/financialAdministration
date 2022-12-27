package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LargerThanAmountCategorizationRuleTest {

    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR");

    @Test
    public void transactionWithLargerThan0Amount_shouldBeAddedToCategory() {
        final Transaction toCategorize = new Transaction(null, null, null, null, Money.of(1, EUR), null);
        final Category category = new Category("someCategory");
        final Money zero = Money.zero(EUR);
        LargerThanAmountCategorizationCategorizationRule largerThanAmountCategorizationRule = new LargerThanAmountCategorizationCategorizationRule(category, zero);
        assertTrue(largerThanAmountCategorizationRule.categorize(toCategorize));
        assertThat(category.transactions(), hasItems(toCategorize));
    }

    @Test
    public void transactionWithSmallerThan0Amount_shouldNotBeAddedToCategory() {
        final Transaction toCategorize = new Transaction(null, null, null, null, Money.of(-1, EUR), null);
        final Category category = new Category("someCategory");
        final Money zero = Money.zero(EUR);
        LargerThanAmountCategorizationCategorizationRule largerThanAmountCategorizationRule = new LargerThanAmountCategorizationCategorizationRule(category, zero);
        assertFalse(largerThanAmountCategorizationRule.categorize(toCategorize));
        assertThat(category.transactions(), not(hasItems(toCategorize)));
    }
}
