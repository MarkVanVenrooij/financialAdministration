package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BudgetCalculationCheck {

    private CategoryRepository categoryRepository;
    private BudgetFactory budgetFactory;
    private Category category;

    //FIXME while running tests there are 3 eventsync listeners at some point because the thing is static

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
        Budget budget = budgetFactory.createBudget(category.name(), Year.of(2018));

        assertEquals(Money.zero(Monetary.getCurrency("EUR")), budget.remaining());
    }

    @Test
    void zeroBudgetWithSingleTransactionHasNegativeDifference() {
        final BudgetRepository budgetRepository = new BudgetRepository();
        //TODO there should be some orchestration so that the eventlistener is instantiated automatically when context starts
        new AmountUsedUpdater(budgetRepository);

        Budget budget = budgetFactory.createBudget(category.name(), Year.of(2018));
        budgetRepository.storeBudget(budget);

        addAndStoreTransactionInYear(Year.of(2018));

        Optional<Budget> optionalBudget = budgetRepository.findExistingBudgetByNameAndYear(budget.categoryName(), budget.year());
        assertTrue(optionalBudget.isPresent());
        assertEquals(Money.of(-1, "EUR"), optionalBudget.get().remaining());
    }

    @Test
    void tenEuroBudgetWithSingleTransactionHasPositiveDifference() {
        final BudgetRepository budgetRepository = new BudgetRepository();
        new AmountUsedUpdater(budgetRepository);

        Budget budget = budgetFactory.createBudget(category.name(), Year.of(2018));
        budget.setAmountPlanned(Money.of(10, "EUR"));
        budgetRepository.storeBudget(budget);

        addAndStoreTransactionInYear(Year.of(2018));

        Optional<Budget> optionalBudget = budgetRepository.findExistingBudgetByNameAndYear(budget.categoryName(), budget.year());
        assertTrue(optionalBudget.isPresent());
        assertEquals(Money.of(9, "EUR"), optionalBudget.get().remaining());
    }

    @Test
    void twoBudgetsAreUpdatedWhithMultipleTransactions() {
        final BudgetRepository budgetRepository = new BudgetRepository();

        new AmountUsedUpdater(budgetRepository);

        Budget budget1 = budgetFactory.createBudget(category.name(), Year.of(2017));
        budget1.setAmountPlanned(Money.of(10, "EUR"));
        budgetRepository.storeBudget(budget1);

        Budget budget2 = budgetFactory.createBudget(category.name(), Year.of(2018));
        budget2.setAmountPlanned(Money.of(20, "EUR"));
        budgetRepository.storeBudget(budget2);

        addAndStoreTransactionInYear(Year.of(2017));
        addAndStoreTransactionInYear(Year.of(2018));
        addAndStoreTransactionInYear(Year.of(2018));

        Optional<Budget> optionalBudget = budgetRepository.findExistingBudgetByNameAndYear(budget1.categoryName(), budget1.year());
        assertTrue(optionalBudget.isPresent());
        assertEquals(Money.of(9, "EUR"), optionalBudget.get().remaining());

        Optional<Budget> optionalBudget2 = budgetRepository.findExistingBudgetByNameAndYear(budget2.categoryName(), budget2.year());
        assertTrue(optionalBudget2.isPresent());
        assertEquals(Money.of(18, "EUR"), optionalBudget2.get().remaining());
    }

    private void addAndStoreTransactionInYear(Year year) {
        final Transaction transaction = createOneEuroTransactionInYear(year);
        category.addTransactions(transaction);
        categoryRepository.storeCategory(category);
    }

    private Transaction createOneEuroTransactionInYear(Year year) {
        return new Transaction("111", LocalDate.of(year.getValue(), 1, 1), "1", Money.of(1, "EUR"), "desc");
    }
}
