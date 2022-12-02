package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TransactionTest {

    @Test
    public void initialValueObjectChecks() {
        final String accountNumber = "contraAccountNumber";
        final LocalDate date = LocalDate.of(2017, 1, 1);
        final String contraAccount = "contraAccountNumber";
        final Money amount = Money.of(1, Monetary.getCurrency("EUR"));
        final String description = "description";

        Transaction transaction1 = new Transaction(accountNumber, date, contraAccount, amount, description);
        Transaction transaction2 = new Transaction(accountNumber, date, contraAccount, amount, description);

        Transaction transaction3 = new Transaction("anotherAccount", date, contraAccount, amount, description);
        Transaction transaction4 = new Transaction(accountNumber, LocalDate.of(2018, 1, 1), contraAccount, amount, description);
        Transaction transaction5 = new Transaction(accountNumber, date, "anotherContraAccount", amount, description);
        Transaction transaction6 = new Transaction(accountNumber, date, contraAccount, Money.of(2, Monetary.getCurrency("EUR")), description);
        Transaction transaction7 = new Transaction(accountNumber, date, contraAccount, amount, "anotherDescription");

        assertEquals(transaction1, transaction2);
        assertEquals(transaction2, transaction1);

        assertNotEquals(null, transaction1);
        assertNotEquals(transaction1, transaction3);
        assertNotEquals(transaction1, transaction4);
        assertNotEquals(transaction1, transaction5);
        assertNotEquals(transaction1, transaction6);
        assertNotEquals(transaction1, transaction7);

        assertEquals(accountNumber, transaction1.accountNumber());
        assertEquals(date, transaction1.date());
        assertEquals(contraAccount, transaction1.contraAccountNumber());
        assertEquals(amount, transaction1.amount());
        assertEquals(description, transaction1.description());
    }
}
