package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetTest {

    private static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private static final Money EUR_20 = Money.of(20, EUR);

    @Test
    void initalEntityChecks() {
        final String categoryName = "category";
        final Money amount = Money.of(1, EUR);
        final Year year = Year.of(2017);
        final Budget budget1 = new Budget(categoryName, year, amount);

        final Budget budget2 = new Budget(categoryName, year, EUR_20);
        final Budget budget3 = new Budget(categoryName, year, EUR_20);
        final Budget budget4 = new Budget(categoryName, Year.of(2016), EUR_20);
        final Budget budget5 = new Budget("anotherCategory", year, EUR_20);

        assertEquals(budget1, budget2);
        assertEquals(budget2, budget1);
        assertEquals(budget3, budget1);

        assertNotEquals(null, budget1);
        assertNotEquals(budget1, budget4);
        assertNotEquals(budget1, budget5);

        assertEquals(categoryName, budget1.categoryName());
        assertEquals(year, budget1.year());

        assertEquals(budget1, budget2);
        assertEquals(amount, budget1.amountPlanned());
    }

    @Test
    void amountRemainingIsZeroOnNewBudget() {
        Budget budget = new Budget("cat", Year.of(2018), Money.zero(EUR));
        assertEquals(Money.zero(EUR), budget.remaining());
    }

    @Test
    void amountRemainingIsAmountPlannedWithoutAnySpending() {
        Budget budget = new Budget("cat", Year.of(2018), Money.of(10, EUR));
        assertEquals(Money.of(10, EUR), budget.remaining());
    }

    @Test
    void amountRemainingIsNegativeWithOnlySpending() {
        Budget budget = new Budget("cat", Year.of(2018), Money.zero(EUR));
        Money amount = Money.of(10, EUR);
        budget.updateAmountUsed(amount);
        assertEquals(amount.multiply(-1), budget.remaining());
    }
}
