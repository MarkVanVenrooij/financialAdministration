package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BudgetRepositoryTest {

    private CategoryRepository categoryRepository;
    private BudgetFactory budgetFactory;
    private BudgetRepository budgetRepository;
    private String categoryName;
    private static final Money EUR_20 = Money.of(20, "EUR");

    @BeforeEach
    void setup() {
        budgetRepository = new BudgetRepository();
        categoryRepository = new CategoryRepository();
        categoryName = "cat";
        categoryRepository.storeCategory(new Category(categoryName));
        budgetFactory = new BudgetFactory(categoryRepository);
    }

    @Test
    void findNonExistingBudgetByNameAndYear() {
        BudgetRepository budgetRepository = new BudgetRepository();
        assertThrows(BudgetDoesNotExist.class ,() -> budgetRepository.findExistingBudgetByNameAndYear("budgetCategory", Year.of(2017)));
    }

    @Test
    void findExistingBudgetByNameAndYear() {
        final Budget budget = budgetFactory.createEvenlySpreadBudget(categoryName, Year.of(2018), EUR_20);
        budgetRepository.storeBudget(budget);

        final Budget budgetFound = budgetRepository.findExistingBudgetByNameAndYear(categoryName, Year.of(2018));

        assertEquals(budget, budgetFound);
    }

    @Test
    void findNonExistingBudgetsByName() {
        final Set<Budget> budgets = budgetRepository.findExistingBudgetByName(categoryName);

        assertTrue(budgets.isEmpty());
    }

    @Test
    void findExistingBudgetByName() {
        final Budget budget = budgetFactory.createEvenlySpreadBudget(categoryName, Year.of(2018), EUR_20);
        budgetRepository.storeBudget(budget);

        final Set<Budget> budgets = budgetRepository.findExistingBudgetByName(categoryName);

        assertFalse(budgets.isEmpty());
        assertEquals(1, budgets.size());

    }

    @Test
    void findExistingBudgetsByName() {
        final Budget budget1 = budgetFactory.createEvenlySpreadBudget(categoryName, Year.of(2017), EUR_20);
        final Budget budget2 = budgetFactory.createEvenlySpreadBudget(categoryName, Year.of(2018), EUR_20);
        budgetRepository.storeBudget(budget1);
        budgetRepository.storeBudget(budget2);

        final Set<Budget> budgets = budgetRepository.findExistingBudgetByName(categoryName);

        assertFalse(budgets.isEmpty());
        assertEquals(2, budgets.size());
        final Iterator<Budget> iterator = budgets.iterator();
        assertEquals(budget1, iterator.next());
        assertEquals(budget2, iterator.next());
    }

    @Test
    void findAllBudgetsByYearInEmptyRepo() {
        final Set<Budget> budgets = budgetRepository.findAllBudgetsByYear(Year.of(2018));

        assertTrue(budgets.isEmpty());
    }

    @Test
    void findSingleExistingBudgetsByYear() {
        final Budget budget = budgetFactory.createEvenlySpreadBudget(categoryName, Year.of(2018), EUR_20);
        budgetRepository.storeBudget(budget);

        final Set<Budget> budgets = budgetRepository.findAllBudgetsByYear(Year.of(2018));

        assertFalse(budgets.isEmpty());
        assertEquals(1, budgets.size());
    }

    @Test
    void findExistingBudgetsByYear() {
        final Budget budget1 = budgetFactory.createEvenlySpreadBudget(categoryName, Year.of(2017), EUR_20);
        final Budget budget2 = budgetFactory.createEvenlySpreadBudget(categoryName, Year.of(2018), EUR_20);
        budgetRepository.storeBudget(budget1);
        budgetRepository.storeBudget(budget2);

        final Set<Budget> budgets = budgetRepository.findAllBudgetsByYear(Year.of(2018));

        assertFalse(budgets.isEmpty());
        assertEquals(1, budgets.size());
        final Iterator<Budget> iterator = budgets.iterator();
        assertEquals(budget2, iterator.next());
    }

    @Test
    void findMultipleExistingBudgetsByYear() {
        categoryRepository.storeCategory(new Category("cat2"));
        final Budget budget1 = budgetFactory.createEvenlySpreadBudget(categoryName, Year.of(2018), EUR_20);
        final Budget budget2 = budgetFactory.createEvenlySpreadBudget("cat2", Year.of(2018), EUR_20);
        budgetRepository.storeBudget(budget1);
        budgetRepository.storeBudget(budget2);

        final Set<Budget> budgets = budgetRepository.findAllBudgetsByYear(Year.of(2018));

        assertFalse(budgets.isEmpty());
        assertEquals(2, budgets.size());
        final Iterator<Budget> iterator = budgets.iterator();
        assertEquals(budget1, iterator.next());
        assertEquals(budget2, iterator.next());
    }

    @Test
    void storeAndFindBudgetByNameAndYear() {
        final String categoryName = "budgetCategory";
        final Year year = Year.of(2017);
        final Budget budget = new Budget(categoryName, year, Collections.singletonMap(Month.JANUARY, EUR_20));
        final BudgetRepository budgetRepository = new BudgetRepository();

        budgetRepository.storeBudget(budget);

        final Budget budgetFound = budgetRepository.findExistingBudgetByNameAndYear(categoryName, year);
        assertEquals(year, budgetFound.year());
        assertEquals(categoryName, budgetFound.categoryName());
    }
}
