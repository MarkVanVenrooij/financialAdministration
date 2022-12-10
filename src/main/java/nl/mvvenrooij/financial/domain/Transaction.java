package nl.mvvenrooij.financial.domain;


import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public final class Transaction {
    private final String accountNumber;
    private final LocalDate date;
    private final String contraAccountNumber;

    private final String counterParty;
    private final Money amount;
    private final String description;

    public Transaction(final String accountNumber, final LocalDate date, final String contraAccountNumber, final String counterParty, final Money amount, final String description) {
        this.accountNumber = accountNumber;
        this.date = date;
        this.contraAccountNumber = contraAccountNumber;
        this.counterParty = counterParty;
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

    public String counterParty() {
        return counterParty;
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
                Objects.equals(counterParty, that.counterParty) &&
                Objects.equals(contraAccountNumber, that.contraAccountNumber) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, date, counterParty, contraAccountNumber, amount, description);
    }

    @Override
    public String toString() {
        MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder
                .of(Locale.GERMAN)
                .set(CurrencyStyle.SYMBOL)
                .set("pattern", "0.00")
                .build());
        final String amountFormatted = customFormat.format(amount);
        return "Transaction{" +
                "accountNumber='" + accountNumber + '\'' +
                ", date=" + date +
                ", contraAccountNumber='" + contraAccountNumber + '\'' +
                ", counterParty='" + counterParty + '\'' +
                ", amount=" + amountFormatted +
                ", description='" + description + '\'' +
                '}';
    }
}
