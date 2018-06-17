package nl.mvvenrooij.financial.domain;

import nl.mvvenrooij.financial.domainevents.EventBus;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Category {
    private static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private final String name;

    private List<Transaction> transactions = new ArrayList<>();

    public Category(final String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void addTransactions(final Transaction... transactions) {
        this.transactions.addAll(Arrays.asList(transactions));
        EventBus.publish(new CategoryUpdated(this));
    }

    public Money totalInInterval(final LocalDate startDate, final LocalDate endDate) {
        return transactions.stream()
                .filter(t -> (startDate.isBefore(t.date()) || startDate.isEqual(t.date())) &&
                        (endDate.isEqual(t.date()) || endDate.isAfter(t.date())))
                .map(Transaction::amount)
                .reduce(Money.zero(EUR), Money::add);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
