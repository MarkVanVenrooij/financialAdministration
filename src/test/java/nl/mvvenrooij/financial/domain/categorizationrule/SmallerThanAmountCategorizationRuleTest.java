package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmallerThanAmountCategorizationRuleTest {

    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR");

    @Test
    public void transactionWithSmallerThan0Amount_shouldBeAddedToCategory() {
        final Transaction toCategorize = new Transaction(null, null, null, null, Money.of(-1, EUR), null);
        final Category category = new Category("someCategory");
        final Money zero = Money.of(10, EUR);
        SmallerThanAmountCategorizationRule largerThanAmountRule = new SmallerThanAmountCategorizationRule(category, zero);
        assertTrue(largerThanAmountRule.matches(toCategorize));
    }

    @Test
    public void transactionWithLargerThan0Amount_shouldNotBeAddedToCategory() {
        final Transaction toCategorize = new Transaction(null, null, null, null, Money.of(1, EUR), null);
        final Category category = new Category("someCategory");
        final Money zero = Money.zero(EUR);
        SmallerThanAmountCategorizationRule largerThanAmountRule = new SmallerThanAmountCategorizationRule(category, zero);
        assertFalse(largerThanAmountRule.matches(toCategorize));
    }
}
