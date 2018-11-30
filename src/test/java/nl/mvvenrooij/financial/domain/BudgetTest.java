package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetTest {

    static final CurrencyUnit EUR = Monetary.getCurrency("EUR");

    @Test
    void initalEntityChecks() {
        final String categoryName = "category";
        final Year year = Year.of(2017);
        final Budget budget1 = new Budget(categoryName, year);

        final Budget budget2 = new Budget(categoryName, year);
        final Budget budget3 = new Budget(categoryName, year);
        final Budget budget4 = new Budget(categoryName, Year.of(2016));
        final Budget budget5 = new Budget("anotherCategory", year);

        assertTrue(budget1.equals(budget2));
        assertTrue(budget2.equals(budget1));
        assertTrue(budget3.equals(budget1));

        assertFalse(budget1.equals(null));
        assertFalse(budget1.equals(budget4));
        assertFalse(budget1.equals(budget5));

        assertEquals(categoryName, budget1.categoryName());
        assertEquals(year, budget1.year());
        final Money amount = Money.of(1, EUR);
        budget1.setAmountPlanned(amount);
        assertTrue(budget1.equals(budget2));
        assertEquals(amount, budget1.amountPlanned());
    }

    @Test
    void amountRemainingIsZeroOnNewBudget() {
        Budget budget = new Budget("cat", Year.of(2018));
        assertEquals(Money.zero(EUR), budget.remaining());
    }

    @Test
    void amountRemainingIsAmountPlannedWithoutAnySpending() {
        Budget budget = new Budget("cat", Year.of(2018));
        Money amount = Money.of(10, EUR);
        budget.setAmountPlanned(amount);
        assertEquals(amount, budget.remaining());
    }

    @Test
    void amountRemainingIsNegativeWithOnlySpending() {
        Budget budget = new Budget("cat", Year.of(2018));
        Money amount = Money.of(10, EUR);
        budget.setAmountUsed(amount);
        assertEquals(amount.multiply(-1), budget.remaining());
    }
}
