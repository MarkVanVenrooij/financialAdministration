package nl.mvvenrooij.financial.presentation;

import nl.mvvenrooij.financial.domain.*;
import nl.mvvenrooij.financial.categorization.CategorizationRules;
import nl.mvvenrooij.financial.categorization.ContraAccountCatgegoryRule;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Objects;

public class AdministrationOverview {
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final BudgetRepository budgetRepository = new BudgetRepository();
    private final BudgetFactory budgetFactory = new BudgetFactory(categoryRepository);
    private static final CurrencyUnit EUR = Monetary.getCurrency("EUR");
    private final Category uncategorized = new Category("UNCATEGORIZED");
    private final Category salary = new Category("Salary");
    private final Category rent = new Category("Rent");
    private final Category energy = new Category("Energy");
    private final Category taxes = new Category("Taxes");

    public AdministrationOverview() {
        new AmountUsedUpdater(budgetRepository);
        createCategories();
        createBudgets2022();
        CategorizationRules categorizationRules = createCategorizationRules();

        final List<Transaction> transactionsList = importTransactions();

        categorizationRules.apply(transactionsList);

        printCategoryAmounts();
        printUncatgegorizedItems();
    }

    private void printCategoryAmounts() {
        for (Category cat : categoryRepository.categories()) {
            System.out.print(cat.name());
            System.out.print(" amount ");
            System.out.println(cat.totalInInterval(LocalDate.of(2022,1,1),LocalDate.of(2022,12,31)));
        }

    }
    private void printUncatgegorizedItems() {
        System.out.println();
        System.out.println("###########  UNCATEGORIZED");
        System.out.println();
        for (Transaction t: uncategorized.transactions()) {
            System.out.println(t);
        }
    }

    private CategorizationRules createCategorizationRules() {
        CategorizationRules categorizationRules = new CategorizationRules(uncategorized);
        categorizationRules.add(new ContraAccountCatgegoryRule(salary, "NL98INGB0003856625"));
        categorizationRules.add(new ContraAccountCatgegoryRule(taxes, "NL98INGB0003856626"));
        categorizationRules.add(new ContraAccountCatgegoryRule(rent, "NL98INGB0003856627"));
        categorizationRules.add(new ContraAccountCatgegoryRule(energy, "NL98INGB0003856628"));
        return categorizationRules;
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

    private void createCategories() {
        categoryRepository.storeCategory(salary);
        categoryRepository.storeCategory(rent);
        categoryRepository.storeCategory(energy);
        categoryRepository.storeCategory(taxes);
        categoryRepository.storeCategory(new Category("Groceries"));
        categoryRepository.storeCategory(new Category("Coffee to go"));
        categoryRepository.storeCategory(uncategorized);
    }

    private void createBudgets2022() {
        budgetFactory.createBudget("Salary", Year.of(2022), Money.of(1000, EUR));
        budgetFactory.createBudget("Rent", Year.of(2022), Money.of(500, EUR));
        budgetFactory.createBudget("Groceries", Year.of(2022), Money.of(300, EUR));
    }

    public static void main(final String... args) {
        new AdministrationOverview();
    }
}