package nl.mvvenrooij.financial.domain;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class BudgetRepository {
    private Map<CategoryYear, Budget> budgets = new HashMap<>();

    public Optional<Budget> findExistingBudgetByNameAndYear(final String budgetCategory, final Year year) {
        return Optional.ofNullable(budgets.get(new CategoryYear(budgetCategory, year)));
    }

    public void storeBudget(final Budget budget) {
        budgets.put(new CategoryYear(budget.categoryName(), budget.year()), budget);
    }

    public Set<Budget> findExistingBudgetByName(final String categoryName) {
        return budgets.entrySet().stream()
                .filter((e) -> e.getKey().category.equals(categoryName)).map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    public Set<Budget> findAllBudgetsByYear(final Year year) {
        return budgets.entrySet().stream()
                .filter((e) -> e.getKey().year.equals(year)).map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    private class CategoryYear {
        private final String category;
        private final Year year;

        public CategoryYear(String category, Year year) {
            this.category = category;
            this.year = year;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CategoryYear that = (CategoryYear) o;
            return Objects.equals(category, that.category) &&
                    Objects.equals(year, that.year);
        }

        @Override
        public int hashCode() {
            return Objects.hash(category, year);
        }
    }

}
