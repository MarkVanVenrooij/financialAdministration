package nl.mvvenrooij.financial.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    private static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private static final Money EURO_10 = Money.of(10, EUR);
    private static final Money EURO_11 = Money.of(11, EUR);
    private static final Money EURO_12 = Money.of(12, EUR);
    private static final Money EURO_20 = Money.of(20, EUR);
    private static final Money EURO_21 = Money.of(21, EUR);
    private static final Money EURO_33 = Money.of(33, EUR);
    private static final String DESCRIPTION = "description";
    private static final String CONTRA_ACCOUNT = "contraAccountNumber";
    private static final String ACCOUNT_NUMBER = "contraAccountNumber";
    private static final LocalDate JANUARI_01_2017 = LocalDate.of(2017, 1, 1);
    private static final LocalDate DECEMBER_31_2017 = LocalDate.of(2017, 12, 31);

    @Test
    void initialEntityChecks() {
        final String categoryName = "category";
        final Category category1 = new Category(categoryName);
        final Category category2 = new Category(categoryName);
        final Category category3 = new Category("anotherName");
        assertTrue(category1.equals(category2));
        assertTrue(category2.equals(category1));
        assertFalse(category1.equals(null));
        assertFalse(category1.equals(category3));
        assertEquals(categoryName, category1.name());
    }

    @Test
    void withoutTransactionsTotalsToZero() {
        final String categoryName = "category";
        final Category category = new Category(categoryName);
        assertEquals(Money.zero(EUR), category.totalInInterval(JANUARI_01_2017, DECEMBER_31_2017));
    }

    @Test
    void withOneTransactionsTotalsToTransactionAmount() {
        final String categoryName = "category";
        final Category category = new Category(categoryName);
        final LocalDate date = LocalDate.of(2017, 12, 30);
        final Transaction transaction = new Transaction(ACCOUNT_NUMBER, date, CONTRA_ACCOUNT, EURO_10, DESCRIPTION);
        category.addTransactions(transaction);
        assertEquals(EURO_10, category.totalInInterval(JANUARI_01_2017, DECEMBER_31_2017));
    }

    @Test
    void withMultipleTransactionsTotalsToTransactionAmount() {
        final String categoryName = "category";
        final Category category = new Category(categoryName);
        final LocalDate date = LocalDate.of(2017, 12, 30);
        final Transaction transaction1 = new Transaction(ACCOUNT_NUMBER, date, CONTRA_ACCOUNT, EURO_10, DESCRIPTION);
        final Transaction transaction2 = new Transaction(ACCOUNT_NUMBER, date, CONTRA_ACCOUNT, EURO_11, DESCRIPTION);
        final Transaction transaction3 = new Transaction(ACCOUNT_NUMBER, date, CONTRA_ACCOUNT, EURO_12, DESCRIPTION);
        category.addTransactions(transaction1, transaction2, transaction3);
        assertEquals(EURO_33, category.totalInInterval(JANUARI_01_2017, DECEMBER_31_2017));
    }

    @Test
    void withTwoIndentocalTransactionsTotalsToTransactionAmount() {
        final String categoryName = "category";
        final Category category = new Category(categoryName);
        final LocalDate date = LocalDate.of(2017, 12, 30);
        final Transaction transaction1 = new Transaction(ACCOUNT_NUMBER, date, CONTRA_ACCOUNT, EURO_10, DESCRIPTION);
        final Transaction transaction2 = new Transaction(ACCOUNT_NUMBER, date, CONTRA_ACCOUNT, EURO_10, DESCRIPTION);
        category.addTransactions(transaction1, transaction2);
        assertEquals(EURO_20, category.totalInInterval(JANUARI_01_2017, DECEMBER_31_2017));
    }

    @Test
    void withTransactionOutsideOfIntervalTotalsToZero() {
        final String categoryName = "category";
        final Category category = new Category(categoryName);
        final Transaction transaction1 = new Transaction(ACCOUNT_NUMBER, LocalDate.of(2018, 1, 1), CONTRA_ACCOUNT, EURO_10, DESCRIPTION);
        final Transaction transaction2 = new Transaction(ACCOUNT_NUMBER, LocalDate.of(2016, 12, 31), CONTRA_ACCOUNT, EURO_10, DESCRIPTION);
        category.addTransactions(transaction1, transaction2);
        assertEquals(Money.zero(EUR), category.totalInInterval(JANUARI_01_2017, DECEMBER_31_2017));
    }

    @Test
    void withTransactionAtEdgeOfIntervalTotalsToTransactionAmount() {
        final String categoryName = "category";
        final Category category = new Category(categoryName);
        final Transaction transaction1 = new Transaction(ACCOUNT_NUMBER, JANUARI_01_2017, CONTRA_ACCOUNT, EURO_10, DESCRIPTION);
        final Transaction transaction2 = new Transaction(ACCOUNT_NUMBER, DECEMBER_31_2017, CONTRA_ACCOUNT, EURO_11, DESCRIPTION);
        category.addTransactions(transaction1, transaction2);
        assertEquals(EURO_21, category.totalInInterval(JANUARI_01_2017, DECEMBER_31_2017));
    }

    @Test
    void transactionsReturnsCopy() {
        final String categoryName = "category";
        final Category category = new Category(categoryName);
        final Transaction transaction1 = new Transaction(ACCOUNT_NUMBER, JANUARI_01_2017, CONTRA_ACCOUNT, EURO_10, DESCRIPTION);
        final Transaction transaction2 = new Transaction(ACCOUNT_NUMBER, DECEMBER_31_2017, CONTRA_ACCOUNT, EURO_11, DESCRIPTION);
        category.addTransactions(transaction1);
        List<Transaction> transactions = category.transactions();
        transactions.set(0,transaction2);
        assertEquals(EURO_10, category.totalInInterval(JANUARI_01_2017, DECEMBER_31_2017));
    }
}
