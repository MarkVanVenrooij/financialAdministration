package nl.mvvenrooij.financial.presentation;

import nl.mvvenrooij.financial.domain.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetUsageTest {


    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private BudgetRepository budgetRepository;
    private Category someCat;
    private BudgetFactory factory;
    private Budget budget;

    @BeforeEach
    public void setUp() {
        budgetRepository = new BudgetRepository();
        new AmountUsedUpdater(budgetRepository);
        CategoryRepository categoryRepository = new CategoryRepository();
        someCat = new Category("someCat");
        categoryRepository.storeCategory(someCat);
        factory = new BudgetFactory(categoryRepository);

        budget = factory.createBudget("someCat", Year.of(2023), Money.of(1200, EUR));
        budgetRepository.storeBudget(budget);

        someCat.addTransactions(new Transaction("account", LocalDate.of(2023, Month.JANUARY, 3), "contra", "counterparty", Money.of(70, EUR), "desc"));
    }

    @Test
    public void SeeIfSpendingIsOnTrackInJanuariComparedToBudget_evenlyMonthlySpread() {
        final Money expectedAmountLeftInJanuary = Money.of(30, EUR);

        assertEquals(expectedAmountLeftInJanuary, budget.amountLeftMonth(Month.JANUARY));
    }

    @Test
    public void SeeIfSpendingIsOnTrackInFebruariComparedToBudget_evenlyMonthlySpread() {

        final Money expectedAmountLeftInJanuary = Money.of(130, EUR);

        assertEquals(expectedAmountLeftInJanuary, budget.amountLeftMonth(Month.FEBRUARY));
    }
}
