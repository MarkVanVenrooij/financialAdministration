package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Budget {
    public static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private final String categoryName;
    private final Year year;

    private final Map<Month, Money> amountPlanned;
    private Map<Month, Money> amountUsed = new HashMap<>();

    Budget(final String categoryName, final Year year, final Map<Month, Money> amountPlanned) {
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
        return amountPlanned.values().stream().reduce(Money.zero(EUR), Money::add);
    }

    private Money amountUsed() {
        return amountUsed.values().stream().reduce(Money.zero(EUR), Money::add);
    }

    public void updateAmountUsed(final Map<Month, Money> amountUsed) {
        this.amountUsed = amountUsed;
    }

    public Money remaining() {
        return amountPlanned().subtract(amountUsed());
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
                ", amountLeft=" + amountPlanned().subtract(amountUsed()) +
                "}\n";
    }

    public Money amountRemainingInMonth(final Month month) {

        Money amountPlannedTillMonth = this.amountPlanned.entrySet().stream()
                .filter(monthlyBudget -> monthlyBudget.getKey().getValue() <= month.getValue())
                .map(Map.Entry::getValue)
                .reduce(Money.zero(EUR), Money::add);

        Money amountUsedTillMonth = this.amountUsed.entrySet().stream()
                .filter(monthlyBudget -> monthlyBudget.getKey().getValue() <= month.getValue())
                .map(Map.Entry::getValue)
                .reduce(Money.zero(EUR), Money::add);

        return amountPlannedTillMonth.subtract(amountUsedTillMonth);
    }
}
