package nl.mvvenrooij.financial.presentation;

import nl.mvvenrooij.financial.domain.*;
import nl.mvvenrooij.financial.domain.categorizationrule.CategorizationRuleRepository;
import nl.mvvenrooij.financial.domain.categorizationrule.ContraAccountCatgegorizationCategorizationRule;
import nl.mvvenrooij.financial.domain.categorizationrule.CounterPartyCatgegorizationCategorizationRule;
import nl.mvvenrooij.financial.domain.categorizationrule.SmallerThanAmountCategorizationCategorizationRule;
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
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

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
    private final Category groceries = new Category("Groceries");
    private final Category coffeeToGo = new Category("Coffee to go");
    private final Category electronics = new Category("Electronics");
    private final Category other = new Category("Other");

    public AdministrationOverview() {
        new AmountUsedUpdater(budgetRepository);
        createCategories();
        createBudgets2022();
        CategorizationRuleRepository categorizationRuleRepository = createCategorizationRules();
        Categorization categorization = new Categorization(categorizationRuleRepository);

        final List<Transaction> transactionsList = importTransactions();

        for (Transaction transaction : transactionsList) {
            final Optional<Category> categoryOptional = categorization.categorize(transaction);
            categoryOptional.ifPresentOrElse((category -> category.addTransactions(transaction)), () -> uncategorized.addTransactions(transaction));
        }

        printCategoryAmounts();
        printUncatgegorizedItems();
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

    private CategorizationRuleRepository createCategorizationRules() {
        CategorizationRuleRepository categorizationRuleRepository = new CategorizationRuleRepository();

        categorizationRuleRepository.add(new ContraAccountCatgegorizationCategorizationRule(salary, "NL98INGB0003856625"));
        categorizationRuleRepository.add(new ContraAccountCatgegorizationCategorizationRule(taxes, "NL98INGB0003856626"));
        categorizationRuleRepository.add(new ContraAccountCatgegorizationCategorizationRule(rent, "NL98INGB0003856627"));
        categorizationRuleRepository.add(new ContraAccountCatgegorizationCategorizationRule(energy, "NL98INGB0003856628"));
        categorizationRuleRepository.add(new CounterPartyCatgegorizationCategorizationRule(groceries, "counterparty"));
        categorizationRuleRepository.add(new CounterPartyCatgegorizationCategorizationRule(electronics, "electronics"));
        categorizationRuleRepository.add(new SmallerThanAmountCategorizationCategorizationRule(other, Money.of(25, EUR)));
        return categorizationRuleRepository;
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
        categoryRepository.storeCategory(groceries);
        categoryRepository.storeCategory(coffeeToGo);
        categoryRepository.storeCategory(electronics);
        categoryRepository.storeCategory(uncategorized);
        categoryRepository.storeCategory(other);
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
