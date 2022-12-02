package nl.mvvenrooij.financial.presentation;

import nl.mvvenrooij.financial.domain.*;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.Year;

public class AdministrationOverview {
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final BudgetRepository budgetRepository = new BudgetRepository();
    private final BudgetFactory budgetFactory = new BudgetFactory(categoryRepository);
    private static final CurrencyUnit EUR = Monetary.getCurrency("EUR");

    public AdministrationOverview() {

        new AmountUsedUpdater(budgetRepository);
        createCategories();
        createBudgets2022();

        importTransactions();
    }

    private void importTransactions() {

    }

    private void createCategories() {
        categoryRepository.storeCategory(new Category("Salary"));
        categoryRepository.storeCategory(new Category("Rent"));
        categoryRepository.storeCategory(new Category("Groceries"));
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
