package nl.mvvenrooij.financial.presentation;

import nl.mvvenrooij.financial.domain.*;
import nl.mvvenrooij.financial.domainevents.EventBus;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvenSpreadBudgetTest {

    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private static Budget budget;
    private static BudgetFactory factory;

    @BeforeAll
    public static void setUp() {
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
    public void SeeIfSpendingIsOnTrackInJanuaryComparedToBudget_evenlyMonthlySpread() {
        final Money expectedAmountLeftInJanuary = Money.of(30, EUR);

        assertEquals(expectedAmountLeftInJanuary, budget.amountLeftMonth(Month.JANUARY));
    }

    @Test
    public void SeeIfSpendingIsOnTrackInFebruaryComparedToBudget_evenlyMonthlySpread() {

        final Money expectedAmountLeftInFebruary = Money.of(130, EUR);

        assertEquals(expectedAmountLeftInFebruary, budget.amountLeftMonth(Month.FEBRUARY));
    }

    @Test
    public void SeeIfSpendingIsOnTrackInDecemberComparedToBudget_evenlyMonthlySpread() {

        final Money expectedAmountLeftInDecember = Money.of(1130, EUR);

        assertEquals(expectedAmountLeftInDecember, budget.amountLeftMonth(Month.DECEMBER));
    }

    @Test
    public void handleEvenlySpreadIfAmountIsNotDividableBy12_exludingRoundingFactors() {
        final Budget budget2 = factory.createEvenlySpreadBudget("mySpecialCategory", Year.of(2022), Money.of(0.01, EUR));
        final Money expectedAmountLeftInDecember = Money.of(0.01, EUR);

        assertEquals(expectedAmountLeftInDecember, budget2.amountLeftMonth(Month.DECEMBER));
    }
}
