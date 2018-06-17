package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.time.Year;
import java.util.Objects;

public class Budget {
    private final String categoryName;
    private final Year year;
    private Money amountPlanned = Money.zero(Monetary.getCurrency("EUR"));
    private Money amountUsed = Money.zero(Monetary.getCurrency("EUR"));

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

    public Money amountPlanned() {
        return amountPlanned;
    }

    public void setAmountPlanned(final Money amountPlanned) {
        this.amountPlanned = amountPlanned;
    }

    public Money amountUsed() {
        return amountUsed;
    }

    public void setAmountUsed(final Money amountPlanned) {
        this.amountUsed = amountPlanned;
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

    public Money remaining() {
        return amountPlanned.subtract(amountUsed);
    }
}
