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

public class SmallerThanAmountRuleTest {

    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR");

    @Test
    public void transactionWithSmallerThan0Amount_shouldBeAddedToCategory() {
        final Transaction toCategorize = new Transaction(null, null, null, null, Money.of(-1, EUR), null);
        final Category category = new Category("someCategory");
        final Money zero = Money.of(10, EUR);
        SmallerThanAmountRule largerThanAmountRule = new SmallerThanAmountRule(category, zero);
        assertTrue(largerThanAmountRule.categorize(toCategorize));
        assertThat(category.transactions(), hasItems(toCategorize));
    }

    @Test
    public void transactionWithLargerThan0Amount_shouldNotBeAddedToCategory() {
        final Transaction toCategorize = new Transaction(null, null, null, null, Money.of(1, EUR), null);
        final Category category = new Category("someCategory");
        final Money zero = Money.zero(EUR);
        SmallerThanAmountRule largerThanAmountRule = new SmallerThanAmountRule(category, zero);
        assertFalse(largerThanAmountRule.categorize(toCategorize));
        assertThat(category.transactions(), not(hasItems(toCategorize)));
    }
}
