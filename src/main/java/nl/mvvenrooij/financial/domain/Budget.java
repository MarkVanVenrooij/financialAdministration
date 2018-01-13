package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.time.Year;
import java.util.Objects;

public class Budget {
    private final String categoryName;
    private final Year year;
    private Money amount = Money.zero(Monetary.getCurrency("EUR"));

    Budget(final String categoryName, final Year year) {
        this.categoryName = categoryName;
        this.year = year;
    }

    public String categoryName() {
        return categoryName;
    }

    public Year year() {
        return year;
    }

    public Money amount() {
        return amount;
    }

    public void setAmount(final Money amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Budget budget = (Budget) o;
        return Objects.equals(categoryName, budget.categoryName) &&
                Objects.equals(year, budget.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, year);
    }

}
