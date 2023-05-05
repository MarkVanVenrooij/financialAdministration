package nl.mvvenrooij.financial.domain;

import nl.mvvenrooij.financial.domainevents.EventBus;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDate;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetCalculationCheckTest {

    private CategoryRepository categoryRepository;
    private BudgetFactory budgetFactory;
    private Category category;
    private static BudgetRepository budgetRepository;
    private static final CurrencyUnit EUR = Monetary.getCurrency("EUR");

    @BeforeAll
    static void repoSetup() {
        EventBus<CategoryUpdated> eventBus = new EventBus<>();
        budgetRepository = new BudgetRepository();
        Category.setEventBus(eventBus);
        new AmountUsedUpdater(budgetRepository, eventBus);
    }

    @BeforeEach
    void setup() {
        final String categoryName = "category";
        categoryRepository = new CategoryRepository();
        category = new Category(categoryName);
        categoryRepository.storeCategory(category);
        budgetFactory = new BudgetFactory(categoryRepository);
    }

    @Test
    void zeroBudgetWithZeroTransactionsNoDifference() {
        Budget budget = budgetFactory.createEvenlySpreadBudget(category.name(), Year.of(2018), Money.zero(EUR));
        assertEquals(Money.zero(Monetary.getCurrency("EUR")), budget.remaining());
    }

    @Test
    void zeroBudgetWithSingleTransactionHasNegativeDifference() {
        Budget budget = budgetFactory.createEvenlySpreadBudget(category.name(), Year.of(2018), Money.zero(EUR));
        budgetRepository.storeBudget(budget);

        addAndStoreTransactionInYear(Year.of(2018));

        Budget budgetFound = budgetRepository.findExistingBudgetByNameAndYear(budget.categoryName(), budget.year());
        assertEquals(Money.of(-1, "EUR"), budgetFound.remaining());
    }

    @Test
    void twelveEuroBudgetWithSingleTransactionHasPositiveDifference() {
        Budget budget = budgetFactory.createEvenlySpreadBudget(category.name(), Year.of(2018), Money.of(12, EUR));
        budgetRepository.storeBudget(budget);

        addAndStoreTransactionInYear(Year.of(2018));

        Budget budgetFound = budgetRepository.findExistingBudgetByNameAndYear(budget.categoryName(), budget.year());
        assertEquals(Money.of(11, EUR), budgetFound.remaining());
    }

    @Test
    void twoBudgetsAreUpdatedWhithMultipleTransactions() {
        Budget budget1 = budgetFactory.createEvenlySpreadBudget(category.name(), Year.of(2017), Money.of(12, EUR));
        budgetRepository.storeBudget(budget1);

        Budget budget2 = budgetFactory.createEvenlySpreadBudget(category.name(), Year.of(2018), Money.of(24, EUR));
        budgetRepository.storeBudget(budget2);

        addAndStoreTransactionInYear(Year.of(2017));
        addAndStoreTransactionInYear(Year.of(2018));
        addAndStoreTransactionInYear(Year.of(2018));

        Budget budgetFound1 = budgetRepository.findExistingBudgetByNameAndYear(budget1.categoryName(), budget1.year());
        assertEquals(Money.of(11, EUR), budgetFound1.remaining());

        Budget budgetFound2 = budgetRepository.findExistingBudgetByNameAndYear(budget2.categoryName(), budget2.year());
        assertEquals(Money.of(22, EUR), budgetFound2.remaining());
    }

    private void addAndStoreTransactionInYear(Year year) {
        final Transaction transaction = createOneEuroTransactionInYear(year);
        category.addTransactions(transaction);
        categoryRepository.storeCategory(category);
    }

    private Transaction createOneEuroTransactionInYear(Year year) {
        return new Transaction("111", LocalDate.of(year.getValue(), 1, 1), "1", "party", Money.of(1, EUR), "desc");
    }
}
