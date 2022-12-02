package nl.mvvenrooij.financial.domain;

import nl.mvvenrooij.financial.domainevents.EventListener;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Set;

//TODO there should be some orchestration so that the event listener is instantiated automatically when context starts
public class AmountUsedUpdater implements EventListener<CategoryUpdated> {
    private final BudgetRepository budgetRepository;

    public AmountUsedUpdater(final BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
        registerListener();
    }

    @Override
    public void handleEvent(final CategoryUpdated event) {
        final Category category = event.value();
        final Set<Budget> budgets = budgetRepository.findExistingBudgetByName(category.name());
        budgets.forEach((budget) -> updateAndStoreBudget(category, budget));
    }

    private void updateAndStoreBudget(final Category category, final Budget budget) {
        final Money amountUsed = category.totalInInterval(calculateFirstDayOfYear(budget.year()),
                calculateLastDayOfYear(budget.year()));
        budget.updateAmountUsed(amountUsed);
        budgetRepository.storeBudget(budget);
    }

    private LocalDate calculateLastDayOfYear(Year year) {
        return LocalDate.of(year.getValue(), Month.DECEMBER, 31);
    }

    private LocalDate calculateFirstDayOfYear(Year year) {
        return LocalDate.of(year.getValue(), Month.JANUARY, 1);
    }
}
