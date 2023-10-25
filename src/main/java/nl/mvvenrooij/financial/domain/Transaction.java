package nl.mvvenrooij.financial.domain;


import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.time.LocalDate;
import java.util.Locale;

public record Transaction(String accountNumber, LocalDate date, String contraAccountNumber, String counterParty,
                          Money amount, String description) {

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
