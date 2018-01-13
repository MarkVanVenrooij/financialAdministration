package nl.mvvenrooij.financial.domain;

import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetRepositoryTest {
    @Test
    public void findNonExistingBudgetByNameAndYear() {
        BudgetRepository budgetRepository = new BudgetRepository();
        final Optional<Budget> optionalBudget = budgetRepository.findNonExistingBudgetByNameAndYear("budgetCategory", Year.of(2017));
        assertFalse(optionalBudget.isPresent());
    }

    @Test
    public void storeAndFindBudgetByNameAndYear() {
        final String categoryName = "budgetCategory";
        final Year year = Year.of(2017);
        final Budget budget = new Budget(categoryName, year);
        final BudgetRepository budgetRepository = new BudgetRepository();

        budgetRepository.storeBudget(budget);

        final Optional<Budget> optionalBudget = budgetRepository.findNonExistingBudgetByNameAndYear(categoryName, year);
        assertTrue(optionalBudget.isPresent());
        optionalBudget.ifPresent(b -> assertEquals(year, b.year()));
        optionalBudget.ifPresent(b -> assertEquals(categoryName, b.categoryName()));
    }
}
