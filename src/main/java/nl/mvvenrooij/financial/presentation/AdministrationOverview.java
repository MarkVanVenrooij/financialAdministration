package nl.mvvenrooij.financial.presentation;

import nl.mvvenrooij.financial.domain.AmountUsedUpdater;
import nl.mvvenrooij.financial.domain.BudgetFactory;
import nl.mvvenrooij.financial.domain.BudgetRepository;
import nl.mvvenrooij.financial.domain.CategoryRepository;
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

        createBudgets2020();
    }

    private void createBudgets2020() {
        budgetFactory.createBudget("Salary", Year.of(2020), Money.of(1000, EUR));
        budgetFactory.createBudget("Rent", Year.of(2020), Money.of(500, EUR));
        budgetFactory.createBudget("Groceries", Year.of(2020), Money.of(300, EUR));
    }

    public static void main(final String... args) {
        new AdministrationOverview();
    }
}
