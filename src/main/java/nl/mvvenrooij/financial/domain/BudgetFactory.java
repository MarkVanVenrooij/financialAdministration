package nl.mvvenrooij.financial.domain;

import java.time.Year;

public class BudgetFactory {
    private final CategoryRepository categoryRepository;

    public BudgetFactory(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Budget createBudget(final String categoryName, final Year year) {
        if (categoryRepository.findCategoryByName(categoryName).isPresent()) {
            return new Budget(categoryName, year);
        } else {
            throw new CategoryDoesNotExistException();
        }

    }

}
