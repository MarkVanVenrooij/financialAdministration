package nl.mvvenrooij.financial.domain;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BudgetRepository {
    private final Map<CategoryYear, Budget> budgets = new HashMap<>();

    public Budget findExistingBudgetByNameAndYear(final String budgetCategory, final Year year) {
        Budget budget = budgets.get(new CategoryYear(budgetCategory, year));
        if(budget == null) {
            throw new BudgetDoesNotExist();
        }
        return budget;
    }

    public void storeBudget(final Budget budget) {
        budgets.put(new CategoryYear(budget.categoryName(), budget.year()), budget);
    }

    public Set<Budget> findExistingBudgetByName(final String categoryName) {
        return budgets.entrySet().stream()
                .filter((e) -> e.getKey().category.equals(categoryName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    public Set<Budget> findAllBudgetsByYear(final Year year) {
        return budgets.entrySet().stream()
                .filter((e) -> e.getKey().year.equals(year))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    private record CategoryYear(String category, Year year) {
    }

}
