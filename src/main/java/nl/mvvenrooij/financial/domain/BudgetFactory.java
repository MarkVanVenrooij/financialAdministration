package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;

import java.time.Year;

public class BudgetFactory {
    private final CategoryRepository categoryRepository;

    public BudgetFactory(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    public Budget createBudget(final String categoryName, final Year year, final Money amount) {
        if (categoryRepository.findCategoryByName(categoryName).isPresent()) {
            return new Budget(categoryName, year, amount);
        } else {
            throw new CategoryDoesNotExist();
        }

    }

    public Budget createBudgetForYearBasedOnOtherBudget(final String categoryName, Year budgetYear, Budget oldBudget) {

        if (categoryRepository.findCategoryByName(categoryName).isPresent()) {
            return new Budget(categoryName, budgetYear, oldBudget.amountPlanned());
        } else {
            throw new CategoryDoesNotExist();
        }
    }
}
