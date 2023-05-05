package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class BudgetFactory {
    private final CategoryRepository categoryRepository;

    public BudgetFactory(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    public Budget createEvenlySpreadBudget(final String categoryName, final Year year, final Money amount) {
        if (categoryRepository.findCategoryByName(categoryName).isPresent()) {

            Map<Month, Money> amountPlanned = Arrays.stream(Month.values())
                    .map(month -> Collections.singletonMap(month, spreadAmountAmongstTwelfeMonths(amount)))
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


            return new Budget(categoryName, year, amountPlanned);
        } else {
            throw new CategoryDoesNotExist();
        }

    }

    public Budget createBudgetForYearBasedOnOtherBudget(final String categoryName, Year budgetYear, Budget
            oldBudget) {

        if (categoryRepository.findCategoryByName(categoryName).isPresent()) {
            return createEvenlySpreadBudget(categoryName, budgetYear, oldBudget.amountPlanned());
        } else {
            throw new CategoryDoesNotExist();
        }
    }

    private Money spreadAmountAmongstTwelfeMonths(Money amount) {
        return amount.divide(12);
    }
}
