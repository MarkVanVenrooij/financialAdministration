package nl.mvvenrooij.financial.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BudgetFactoryTest {

    private BudgetFactory budgetFactory;

    @BeforeEach
    public void setup() {
        final CategoryRepository categoryRepository = new CategoryRepository();
        Category category = new Category("anExistingCategory");
        categoryRepository.storeCategory(category);
        budgetFactory = new BudgetFactory(categoryRepository);

    }

    @Test
    public void createBudget() {
        assertNotNull(budgetFactory.createBudget("anExistingCategory", Year.of(2017)));
    }

    @Test
    public void createBudgetForCategoryThatNotExists() {
        assertThrows(CategoryDoesNotExistException.class, () -> budgetFactory.createBudget("nonExistingCategory", Year.of(2017)));
    }
}
