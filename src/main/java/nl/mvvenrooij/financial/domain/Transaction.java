package nl.mvvenrooij.financial.domain;


import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.util.Objects;

public final class Transaction {
    private final String accountNumber;
    private final LocalDate date;
    private final String contraAccountNumber;
    private final Money amount;
    private final String description;

    public Transaction(final String accountNumber, final LocalDate date, final String contraAccountNumber, final Money amount, final String description) {
        this.accountNumber = accountNumber;
        this.date = date;
        this.contraAccountNumber = contraAccountNumber;
        this.amount = amount;
        this.description = description;
    }

    public String accountNumber() {
        return accountNumber;
    }

    public LocalDate date() {
        return date;
    }

    public String contraAccountNumber() {
        return contraAccountNumber;
    }

    public Money amount() {
        return amount;
    }

    public String description() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(date, that.date) &&
                Objects.equals(contraAccountNumber, that.contraAccountNumber) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, date, contraAccountNumber, amount, description);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountNumber='" + accountNumber + '\'' +
                ", date=" + date +
                ", contraAccountNumber='" + contraAccountNumber + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
