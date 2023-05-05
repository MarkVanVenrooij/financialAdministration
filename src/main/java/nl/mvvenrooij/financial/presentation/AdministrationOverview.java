package nl.mvvenrooij.financial.presentation;

import nl.mvvenrooij.financial.domain.*;
import nl.mvvenrooij.financial.domain.categorizationrule.CategorizationRule;
import nl.mvvenrooij.financial.domain.categorizationrule.CategorizationRuleRepository;
import nl.mvvenrooij.financial.domainevents.EventBus;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;

public class AdministrationOverview {
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final BudgetRepository budgetRepository = new BudgetRepository();
    private final BudgetFactory budgetFactory = new BudgetFactory(categoryRepository);
    private static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private final Category uncategorized = new Category("UNCATEGORIZED");
    private final CategorizationRuleRepository categorizationRuleRepository = new CategorizationRuleRepository();


    public AdministrationOverview() {

        new AmountUsedUpdater(budgetRepository, new EventBus<>());
        readCategories();
        createBudgets2022();
        readRules();
        Categorization categorization = new Categorization(categorizationRuleRepository);

        final List<Transaction> transactionsList = importTransactions();

        for (Transaction transaction : transactionsList) {
            final Optional<Category> categoryOptional = categorization.categorize(transaction);
            categoryOptional.ifPresentOrElse((category -> category.addTransactions(transaction)), () -> uncategorized.addTransactions(transaction));
        }

        //  printCategoryAmounts();
        //  printUncatgegorizedItems();

        Set<Budget> budgets = budgetRepository.findAllBudgetsByYear(Year.of(2022));
        System.out.println(budgets);
    }

    private void printCategoryAmounts() {
        for (Category cat : categoryRepository.categories()) {
            System.out.print(cat.name());
            System.out.print(" amount ");
            MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder
                    .of(Locale.GERMAN)
                    .set(CurrencyStyle.SYMBOL)
                    .set("pattern", "0.00")
                    .build());

            Money yearlyAmount = cat.totalInInterval(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31));
            final String amountFormatted = customFormat.format(yearlyAmount);
            System.out.println(amountFormatted);
        }
    }

    private void printUncatgegorizedItems() {
        System.out.println();
        System.out.println("###########  UNCATEGORIZED");
        System.out.println();
        for (Transaction t : uncategorized.transactions().stream().filter(t -> t.date().isAfter(LocalDate.of(2021, 12, 31))).toList()) {
            System.out.println(t);
        }
    }

    private List<Transaction> importTransactions() {
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("nl/mvvenrooij/financial/import/transactions.csv")).getFile();
        try {
            Reader in = new FileReader(file);
            RaboTransactionImport transactionImport = new RaboTransactionImport(in);
            return transactionImport.transactions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createBudgets2022() {
        budgetRepository.storeBudget(budgetFactory.createEvenlySpreadBudget("Salary", Year.of(2022), Money.of(1000, EUR)));
        budgetRepository.storeBudget(budgetFactory.createEvenlySpreadBudget("Rent", Year.of(2022), Money.of(-500, EUR)));
        budgetRepository.storeBudget(budgetFactory.createEvenlySpreadBudget("Groceries", Year.of(2022), Money.of(-300, EUR)));
        budgetRepository.storeBudget(budgetFactory.createEvenlySpreadBudget("Other", Year.of(2022), Money.of(0, EUR)));

    }

    public static void main(final String... args) {
        new AdministrationOverview();
    }

    public void saveCategories() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL file = classLoader.getResource("nl/mvvenrooij/financial/import");

        try {
            Writer out = Files.newBufferedWriter(Paths.get(file.getPath() + "/categories.csv"));

            CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT
                    .withHeader("name"));
            for (Category category : categoryRepository.categories()) {
                csvPrinter.printRecord(category.name());
            }
            csvPrinter.flush();
            csvPrinter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readCategories() {
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("nl/mvvenrooij/financial/import/categories.csv")).getFile();
        try {
            Reader in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("name")
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                categoryRepository.storeCategory(new Category(record.get("name")));
            }
        } catch (final IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public void saveRules() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL file = classLoader.getResource("nl/mvvenrooij/financial/import");

        try {
            Writer out = Files.newBufferedWriter(Paths.get(file.getPath() + "/rules.csv"));

            CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT
                    .withHeader("type", "categoryName", "constructorValue"));
            for (CategorizationRule rule : categorizationRuleRepository.categorizationRules()) {
                csvPrinter.printRecord(rule.getClass().getName(), rule.category().name(), rule.constructorValue());
            }
            csvPrinter.flush();
            csvPrinter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readRules() {
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("nl/mvvenrooij/financial/import/rules.csv")).getFile();
        try {
            Reader in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("type", "categoryName", "constructorValue")
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                //some reflection

                Class<? extends CategorizationRule> clazz = (Class<? extends CategorizationRule>) Class.forName(record.get("type"));

                Constructor<?> ctor = clazz.getDeclaredConstructors()[0];

                CategorizationRule rule = (CategorizationRule) ctor.newInstance(categoryRepository.findCategoryByName(record.get("categoryName")).get(), record.get("constructorValue"));

                categorizationRuleRepository.add(rule);
            }
        } catch (final IOException | ClassNotFoundException | IllegalAccessException | InstantiationException |
                       InvocationTargetException ioException) {
            throw new RuntimeException(ioException);
        }
    }

}
