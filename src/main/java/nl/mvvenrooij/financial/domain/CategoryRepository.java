package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CategoryRepository {
    private final Map<String, Category> categories = new HashMap<>();

    public Optional<Category> findCategoryByName(final String name) {
        return Optional.ofNullable(categories.get(name));
    }

    public void storeCategory(final Category category) {
        categories.put(category.name(), category);
    }

    public Money totalAmountInOutInYear(Year year) {
        Money result = Money.zero(Monetary.getCurrency("EUR"));
        return categories.values().stream()
                .map((category -> category.totalInInterval(LocalDate.of(year.getValue(),1,1), LocalDate.of(year.getValue(),12,31))))
                .reduce(result, Money::add);
    }
}
