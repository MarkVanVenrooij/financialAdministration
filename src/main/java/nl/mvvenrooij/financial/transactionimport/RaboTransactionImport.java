package nl.mvvenrooij.financial.transactionimport;

import nl.mvvenrooij.financial.domain.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RaboTransactionImport {

    final List<Transaction> transactions = new ArrayList<>();
    String[] HEADERS = {"IBAN/BBAN", "Munt", "BIC", "Volgnr", "Datum", "Rentedatum", "Bedrag", "Saldo na trn",
            "Tegenrekening IBAN/BBAN", "Naam tegenpartij", "Naam uiteindelijke partij", "Naam initi�rende partij",
            "BIC tegenpartij", "Code", "Batch ID", "Transactiereferentie", "Machtigingskenmerk", "Incassant ID",
            "Betalingskenmerk", "Omschrijving-1", "Omschrijving-2", "Omschrijving-3", "Reden retour", "Oorspr bedrag",
            "Oorspr munt", "Koers"};

    public RaboTransactionImport(final Reader in) throws IOException {
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);
        for (CSVRecord record : records) {
            transactions.add(
                    new Transaction(
                            record.get("IBAN/BBAN"),
                            LocalDate.parse(record.get("Rentedatum")),
                            record.get("Tegenrekening IBAN/BBAN"),
                            Money.of(Double.parseDouble(record.get("Bedrag").replace(',','.')), Monetary.getCurrency("EUR")),
                            record.get("Omschrijving-1")
                                    +" " + record.get("Omschrijving-2")
                                    +" " + record.get("Omschrijving-3")
                    )
            );
        }

    }

    public List<Transaction> transactions() {
        return transactions;
    }
}
