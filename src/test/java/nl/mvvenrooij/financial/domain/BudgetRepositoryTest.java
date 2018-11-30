package nl.mvvenrooij.financial.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BudgetRepositoryTest {

    private CategoryRepository categoryRepository;
    private BudgetFactory budgetFactory;
    private BudgetRepository budgetRepository;
    private String categoryName;

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
        final Optional<Budget> optionalBudget = budgetRepository.findExistingBudgetByNameAndYear("budgetCategory", Year.of(2017));
        assertFalse(optionalBudget.isPresent());
    }

    @Test
    void findExistingBudgetByNameAndYear() {
        final Budget budget = budgetFactory.createBudget(categoryName, Year.of(2018));
        budgetRepository.storeBudget(budget);

        final Optional<Budget> optionalBudget = budgetRepository.findExistingBudgetByNameAndYear(categoryName, Year.of(2018));

        assertTrue(optionalBudget.isPresent());
        assertEquals(budget, optionalBudget.get());
    }

    @Test
    void findNonExistingBudgetsByName() {
        final Set<Budget> budgets = budgetRepository.findExistingBudgetByName(categoryName);

        assertTrue(budgets.isEmpty());
    }

    @Test
    void findExistingBudgetByName() {
        final Budget budget = budgetFactory.createBudget(categoryName, Year.of(2018));
        budgetRepository.storeBudget(budget);

        final Set<Budget> budgets = budgetRepository.findExistingBudgetByName(categoryName);

        assertFalse(budgets.isEmpty());
        assertEquals(1, budgets.size());

    }

    @Test
    void findExistingBudgetsByName() {
        final Budget budget1 = budgetFactory.createBudget(categoryName, Year.of(2017));
        final Budget budget2 = budgetFactory.createBudget(categoryName, Year.of(2018));
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
        final Budget budget = budgetFactory.createBudget(categoryName, Year.of(2018));
        budgetRepository.storeBudget(budget);

        final Set<Budget> budgets = budgetRepository.findAllBudgetsByYear(Year.of(2018));

        assertFalse(budgets.isEmpty());
        assertEquals(1, budgets.size());
    }

    @Test
    void findExistingBudgetsByYear() {
        final Budget budget1 = budgetFactory.createBudget(categoryName, Year.of(2017));
        final Budget budget2 = budgetFactory.createBudget(categoryName, Year.of(2018));
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
        final Budget budget1 = budgetFactory.createBudget(categoryName, Year.of(2018));
        final Budget budget2 = budgetFactory.createBudget("cat2", Year.of(2018));
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
        final Budget budget = new Budget(categoryName, year);
        final BudgetRepository budgetRepository = new BudgetRepository();

        budgetRepository.storeBudget(budget);

        final Optional<Budget> optionalBudget = budgetRepository.findExistingBudgetByNameAndYear(categoryName, year);
        assertTrue(optionalBudget.isPresent());
        optionalBudget.ifPresent(b -> assertEquals(year, b.year()));
        optionalBudget.ifPresent(b -> assertEquals(categoryName, b.categoryName()));
    }
}
