package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryRounding;
import javax.money.RoundingQueryBuilder;
import java.math.RoundingMode;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BudgetFactory {
    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private final CategoryRepository categoryRepository;

    public BudgetFactory(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    public Budget createEvenlySpreadBudget(final String categoryName, final Year year, final Money amount) {
        if (categoryRepository.findCategoryByName(categoryName).isPresent()) {

            final Map<Month, Money> amountPlanned = Arrays.stream(Month.values())
                    .map(month -> Collections.singletonMap(month, spreadAmountAmongstTwelfeMonths(amount)))
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            final Money currentAmountPlanned = amountPlanned.values().stream().reduce(Money.zero(EUR), Money::add);
            final Money remainderAmount = amount.subtract(currentAmountPlanned);

            amountPlanned.put(Month.DECEMBER, amountPlanned.get(Month.DECEMBER).add(remainderAmount));


            return new Budget(categoryName, year, amountPlanned);
        } else {
            throw new CategoryDoesNotExist();
        }

    }

    public Budget createOnceAYearBudget(final String categoryName, final YearMonth yearMonth, final Money amount) {
        if (categoryRepository.findCategoryByName(categoryName).isPresent()) {

            final Map<Month, Money> amountPlanned = new HashMap<>();
            amountPlanned.put(yearMonth.getMonth(), amount);

            return new Budget(categoryName, Year.of(yearMonth.getYear()), amountPlanned);
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
        MonetaryRounding rounding = Monetary.getRounding(
                RoundingQueryBuilder.of().setScale(2).set(RoundingMode.HALF_DOWN).build());
        return amount.divide(12).with(rounding);
    }
}
