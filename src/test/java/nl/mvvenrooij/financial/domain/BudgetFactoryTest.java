package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetFactoryTest {

    private static final String EXISTING_CATEGORY = "anExistingCategory";
    private static final String NON_EXISTING_CATEGORY = "nonExistingCategory";
    private static final Money EUR_24 = Money.of(24, "EUR");
    private static final Year _2018 = Year.of(2018);
    private static final Year _2017 = Year.of(2017);

    private BudgetFactory budgetFactory;
    private BudgetRepository budgetRepository;

    @BeforeEach
    public void setup() {
        final CategoryRepository categoryRepository = new CategoryRepository();
        Category category = new Category(EXISTING_CATEGORY);
        categoryRepository.storeCategory(category);
        budgetRepository = new BudgetRepository();
        budgetFactory = new BudgetFactory(categoryRepository);
    }

    @Test
    public void createBudget() {
        assertNotNull(budgetFactory.createEvenlySpreadBudget(EXISTING_CATEGORY, _2018, EUR_24));
    }

    @Test
    public void createBudgetForCategoryThatNotExists() {
        assertThrows(CategoryDoesNotExist.class, () -> budgetFactory.createEvenlySpreadBudget(NON_EXISTING_CATEGORY, _2018, EUR_24));
    }

    @Test
    public void createBudgetForYearBasedOnOtherBudget() {
        final Budget oldBudget = budgetFactory.createEvenlySpreadBudget(EXISTING_CATEGORY, _2017, EUR_24);
        budgetRepository.storeBudget(oldBudget);

        final Budget budget = budgetFactory.createBudgetForYearBasedOnOtherBudget(EXISTING_CATEGORY, _2018, oldBudget);

        assertNotNull(budget);
        assertEquals(EXISTING_CATEGORY, budget.categoryName());
        assertEquals(_2018, budget.year());
        assertEquals(EUR_24, budget.remaining());

    }

    @Test
    public void createBudgetForYearBasedOnOtherBudgetThrowsExceptionWhenCategoryDoesntExists() {
        assertThrows(CategoryDoesNotExist.class, () -> budgetFactory.createBudgetForYearBasedOnOtherBudget(NON_EXISTING_CATEGORY, _2018, null));
    }
}
