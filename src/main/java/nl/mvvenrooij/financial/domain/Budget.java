package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.time.Month;
import java.time.Year;
import java.util.Objects;

public class Budget {
    private final String categoryName;
    private final Year year;
    private final Money amountPlanned;
    private Money amountUsed = Money.zero(Monetary.getCurrency("EUR"));

    Budget(final String categoryName, final Year year, final Money amountPlanned) {
        this.categoryName = categoryName;
        this.year = year;
        this.amountPlanned = amountPlanned;
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

    public Money amountLeft() {
        return amountPlanned.subtract( amountUsed);
    }

    public void updateAmountUsed(final Money amountUsed) {
        this.amountUsed = amountUsed;
    }

    public Money remaining() {
        return amountPlanned.subtract(amountUsed);
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

    @Override
    public String toString() {
        return "Budget{" +
                "categoryName='" + categoryName + '\'' +
                ", year=" + year +
                ", amountPlanned=" + amountPlanned +
                ", amountUsed=" + amountUsed +
                ", amountLeft=" + amountLeft() +
                "}\n";
    }

    public Money amountLeftMonth(final Month month) {
        return amountPlanned.divide(12).multiply(month.getValue()).subtract(amountUsed);
    }
}
