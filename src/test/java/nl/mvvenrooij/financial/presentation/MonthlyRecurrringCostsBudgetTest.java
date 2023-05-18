package nl.mvvenrooij.financial.presentation;

import nl.mvvenrooij.financial.domain.*;
import nl.mvvenrooij.financial.domainevents.EventBus;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonthlyRecurrringCostsBudgetTest {

    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private Budget budget;
    private BudgetFactory factory;

    @BeforeEach
    public void setUp() {
        BudgetRepository budgetRepository = new BudgetRepository();
        EventBus<CategoryUpdated> eventBus = new EventBus<>();
        new AmountUsedUpdater(budgetRepository, eventBus);
        Category.setEventBus(eventBus);

        CategoryRepository categoryRepository = new CategoryRepository();
        Category someCat = new Category("mySpecialCategory");

        categoryRepository.storeCategory(someCat);
        factory = new BudgetFactory(categoryRepository);

        budget = factory.createEvenlySpreadBudget("mySpecialCategory", Year.of(2023), Money.of(1200, EUR));
        budgetRepository.storeBudget(budget);

        someCat.addTransactions(new Transaction("account", LocalDate.of(2023, Month.JANUARY, 3), "contra", "counterparty", Money.of(70, EUR), "desc"));
    }

    @Test
    public void january() {
        final Money expectedAmountLeftInJanuary = Money.of(30, EUR);

        assertEquals(expectedAmountLeftInJanuary, budget.amountRemainingInMonth(Month.JANUARY));
    }

    @Test
    public void february() {

        final Money expectedAmountLeftInFebruary = Money.of(130, EUR);

        assertEquals(expectedAmountLeftInFebruary, budget.amountRemainingInMonth(Month.FEBRUARY));
    }

    @Test
    public void december() {

        final Money expectedAmountLeftInDecember = Money.of(1130, EUR);

        assertEquals(expectedAmountLeftInDecember, budget.amountRemainingInMonth(Month.DECEMBER));
    }

    @Test
    public void handleRoundingIssues() {
        final Budget budget2 = factory.createEvenlySpreadBudget("mySpecialCategory", Year.of(2022), Money.of(0.01, EUR));
        final Money expectedNovember = Money.zero(EUR);
        final Money expectedDecember = Money.of(0.01, EUR);

        assertEquals(expectedNovember, budget2.amountRemainingInMonth(Month.NOVEMBER));
        assertEquals(expectedDecember, budget2.amountRemainingInMonth(Month.DECEMBER));
    }
}
