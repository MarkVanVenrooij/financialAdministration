package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TransactionTest {

    @Test
    public void initialValueObjectChecks() {
        final String accountNumber = "accountNumber";
        final LocalDate date = LocalDate.of(2017, 1, 1);
        final String contraAccount = "contraAccount";
        final Money amount = Money.of(1, Monetary.getCurrency("EUR"));
        final String description = "description";

        Transaction transaction1 = new Transaction(accountNumber, date, contraAccount, amount, description);
        Transaction transaction2 = new Transaction(accountNumber, date, contraAccount, amount, description);

        Transaction transaction3 = new Transaction("anotherAccount", date, contraAccount, amount, description);
        Transaction transaction4 = new Transaction(accountNumber, LocalDate.of(2018, 1, 1), contraAccount, amount, description);
        Transaction transaction5 = new Transaction(accountNumber, date, "anotherContraAccount", amount, description);
        Transaction transaction6 = new Transaction(accountNumber, date, contraAccount, Money.of(2, Monetary.getCurrency("EUR")), description);
        Transaction transaction7 = new Transaction(accountNumber, date, contraAccount, amount, "anotherDescription");

        assertTrue(transaction1.equals(transaction2));
        assertTrue(transaction2.equals(transaction1));

        assertFalse(transaction1.equals(null));
        assertFalse(transaction1.equals(transaction3));
        assertFalse(transaction1.equals(transaction4));
        assertFalse(transaction1.equals(transaction5));
        assertFalse(transaction1.equals(transaction6));
        assertFalse(transaction1.equals(transaction7));

        assertEquals(accountNumber, transaction1.accountNumber());
        assertEquals(date, transaction1.date());
        assertEquals(contraAccount, transaction1.contraAccount());
        assertEquals(amount, transaction1.amount());
        assertEquals(description, transaction1.description());
    }
}
