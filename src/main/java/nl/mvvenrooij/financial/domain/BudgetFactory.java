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


    public Budget createBudgetForYearBasedOnOtherBudget(final String categoryName, Year budgetYear, Budget oldBudget) {

        if (categoryRepository.findCategoryByName(categoryName).isPresent()) {
            final Budget newBudget = new Budget(categoryName, budgetYear);
            newBudget.setAmountPlanned(oldBudget.amountPlanned());
            return newBudget;
        } else {
            throw new CategoryDoesNotExistException();
        }
    }
}
