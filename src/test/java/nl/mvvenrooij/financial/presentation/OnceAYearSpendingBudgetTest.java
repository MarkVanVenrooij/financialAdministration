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
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OnceAYearSpendingBudgetTest {

    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final BudgetFactory factory = new BudgetFactory(categoryRepository);
    private Budget budget;
    private final Category someCat = new Category("someCat");

    @BeforeEach
    public void setUp() {
        BudgetRepository budgetRepository = new BudgetRepository();
        EventBus<CategoryUpdated> eventBus = new EventBus<>();
        Category.setEventBus(eventBus);
        new AmountUsedUpdater(budgetRepository, eventBus);
        categoryRepository.storeCategory(someCat);
        budget = factory.createOnceAYearBudget("someCat", YearMonth.of(2023, Month.MARCH), Money.of(800, EUR));
        budgetRepository.storeBudget(budget);
    }

    @Test
    public void zeroAmountLeftJanuaryForBudgetInMarch() {
        final Money expectedAmountLeftInJanuary = Money.zero(EUR);

        assertEquals(expectedAmountLeftInJanuary, budget.amountRemainingInMonth(Month.JANUARY));
    }

    @Test
    public void fullBudgetLeftInMarchWithoutSpending() {
        final Money expectedAmountLeftInDecember = Money.of(800, EUR);

        assertEquals(expectedAmountLeftInDecember, budget.amountRemainingInMonth(Month.MARCH));
    }

    @Test
    public void fullBudgetLeftInAprilWithoutSpending() {
        final Money expectedAmountLeftInDecember = Money.of(800, EUR);

        assertEquals(expectedAmountLeftInDecember, budget.amountRemainingInMonth(Month.APRIL));
    }

    @Test
    public void noBudgetLeftInMarchWithSpending() {
        someCat.addTransactions(new Transaction("account", LocalDate.of(2023, Month.MARCH, 3), "contra", "counterparty", Money.of(800, EUR), "desc"));
        final Money expectedAmountLeftInDecember = Money.zero(EUR);

        assertEquals(expectedAmountLeftInDecember, budget.amountRemainingInMonth(Month.MARCH));
    }

    @Test
    public void someBudgetLeftInMarchWithNotFullSpending() {
        someCat.addTransactions(new Transaction("account", LocalDate.of(2023, Month.MARCH, 3), "contra", "counterparty", Money.of(799, EUR), "desc"));
        final Money expectedAmountLeftInMarch = Money.of(1, EUR);

        assertEquals(expectedAmountLeftInMarch, budget.amountRemainingInMonth(Month.MARCH));
    }
}
