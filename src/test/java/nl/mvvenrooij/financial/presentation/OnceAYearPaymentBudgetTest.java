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
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OnceAYearPaymentBudgetTest {

    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private static BudgetRepository budgetRepository;

    @BeforeAll
    public static void setUp() {
        budgetRepository = new BudgetRepository();
        EventBus<CategoryUpdated> eventBus = new EventBus<>();
        Category.setEventBus(eventBus);
        new AmountUsedUpdater(budgetRepository, eventBus);
    }

    @Test
    public void SeeIfSpendingIsOnTrackInJanuaryComparedToBudget_OnceAYearBudgetInMarch() {


        CategoryRepository categoryRepository = new CategoryRepository();
        Category someCat = new Category("someCat");
        categoryRepository.storeCategory(someCat);
        BudgetFactory factory = new BudgetFactory(categoryRepository);

        final Budget budget = factory.createOnceAYearBudget("someCat", YearMonth.of(2023, Month.JANUARY), Money.of(800, EUR));

        budgetRepository.storeBudget(budget);

        final Money expectedAmountLeftInJanuary = Money.of(800, EUR);

        assertEquals(expectedAmountLeftInJanuary, budget.amountLeftMonth(Month.JANUARY));
    }

    @Test
    public void SeeIfSpendingIsOnTrackInFebruaryComparedToBudget_OnceAYearBudgetInMarch() {
        CategoryRepository categoryRepository = new CategoryRepository();
        Category someCat = new Category("someCat");
        categoryRepository.storeCategory(someCat);
        BudgetFactory factory = new BudgetFactory(categoryRepository);

        final Budget budget = factory.createOnceAYearBudget("someCat", YearMonth.of(2023, Month.JANUARY), Money.of(800, EUR));

        budgetRepository.storeBudget(budget);

        final Money expectedAmountLeftInFebruary = Money.of(800, EUR);

        assertEquals(expectedAmountLeftInFebruary, budget.amountLeftMonth(Month.FEBRUARY));
    }

    @Test
    public void SeeIfSpendingIsOnTrackInMarchComparedToBudgetBeforeTransaction_OnceAYearBudgetInMarch() {
        CategoryRepository categoryRepository = new CategoryRepository();
        Category someCat = new Category("someCat");
        categoryRepository.storeCategory(someCat);
        BudgetFactory factory = new BudgetFactory(categoryRepository);

        final Budget budget = factory.createOnceAYearBudget("someCat", YearMonth.of(2023, Month.JANUARY), Money.of(800, EUR));

        budgetRepository.storeBudget(budget);


        final Money expectedAmountLeftInDecember = Money.of(800, EUR);


        assertEquals(expectedAmountLeftInDecember, budget.amountLeftMonth(Month.MARCH));
    }

    @Test
    public void SeeIfSpendingIsOnTrackInMarchComparedToBudgetAfterTransaction_OnceAYearBudgetInMarch() {
        CategoryRepository categoryRepository = new CategoryRepository();
        Category someCat = new Category("someCat");
        categoryRepository.storeCategory(someCat);
        BudgetFactory factory = new BudgetFactory(categoryRepository);

        final Budget budget = factory.createOnceAYearBudget("someCat", YearMonth.of(2023, Month.JANUARY), Money.of(800, EUR));

        budgetRepository.storeBudget(budget);
        someCat.addTransactions(new Transaction("account", LocalDate.of(2023, Month.MARCH, 3), "contra", "counterparty", Money.of(799, EUR), "desc"));


        final Money expectedAmountLeftInDecember = Money.of(1, EUR);
        assertEquals(expectedAmountLeftInDecember, budget.amountLeftMonth(Month.MARCH));
    }

    @Test
    public void SeeIfSpendingIsOnTrackInDecemberComparedToBudget_OnceAYearBudgetInMarch() {
        CategoryRepository categoryRepository = new CategoryRepository();
        Category someCat = new Category("someCat");
        categoryRepository.storeCategory(someCat);
        BudgetFactory factory = new BudgetFactory(categoryRepository);

        final Budget budget = factory.createOnceAYearBudget("someCat", YearMonth.of(2023, Month.JANUARY), Money.of(800, EUR));

        budgetRepository.storeBudget(budget);
        someCat.addTransactions(new Transaction("account", LocalDate.of(2023, Month.MARCH, 3), "contra", "counterparty", Money.of(799, EUR), "desc"));

        final Money expectedAmountLeftInDecember = Money.of(1, EUR);


        assertEquals(expectedAmountLeftInDecember, budget.amountLeftMonth(Month.DECEMBER));
    }
}
