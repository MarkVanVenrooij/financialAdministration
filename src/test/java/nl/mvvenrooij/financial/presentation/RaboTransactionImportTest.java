package nl.mvvenrooij.financial.presentation;

import nl.mvvenrooij.financial.domain.Transaction;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;


public class RaboTransactionImportTest {
    private static final CurrencyUnit EUR = Monetary.getCurrency("EUR");

    @Test
    public void loadFileTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        String file = Objects.requireNonNull(classLoader.getResource("nl/mvvenrooij/financial/import/transactions.csv")).getFile();
        Reader in = new FileReader(file);
        RaboTransactionImport transactionImport = new RaboTransactionImport(in);
        List<Transaction> result = transactionImport.transactions();
        assertEquals(2, result.size());
        Transaction transaction1 = new Transaction("NL18RABO0123459876", LocalDate.of(2022, Month.JANUARY, 2), "NL98INGB0003856625", Money.of(221.49, EUR), "description description 2 description 3");
        Transaction transaction2 = new Transaction("NL18RABO0123459876", LocalDate.of(2022, Month.JANUARY, 2), "NL98ABNA0416961347", Money.of(-200, EUR), "description 3   ");
        assertThat(result, hasItems(transaction1, transaction2));
    }
}
