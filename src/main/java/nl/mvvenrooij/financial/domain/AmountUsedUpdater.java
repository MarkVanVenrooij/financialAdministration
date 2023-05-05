package nl.mvvenrooij.financial.domain;

import nl.mvvenrooij.financial.domainevents.EventBus;
import nl.mvvenrooij.financial.domainevents.EventListener;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//TODO there should be some orchestration so that the event listener is instantiated automatically when context starts
public class AmountUsedUpdater implements EventListener<CategoryUpdated> {
    private final BudgetRepository budgetRepository;

    public AmountUsedUpdater(final BudgetRepository budgetRepository, final EventBus<CategoryUpdated> eventBus) {
        this.budgetRepository = budgetRepository;
        registerListener(eventBus);
    }

    @Override
    public void handleEvent(final CategoryUpdated event) {
        final Category category = event.value();
        final Set<Budget> budgets = budgetRepository.findExistingBudgetByName(category.name());
        budgets.forEach((budget) -> updateAndStoreBudget(category, budget));
    }

    private void updateAndStoreBudget(final Category category, final Budget budget) {
        Map<Month, Money> amountUsed = Arrays.stream(Month.values())
                .map(month -> new Object[]{month, category.totalInInterval(calculateFirstDayOfYearMonth(budget.year(), month), calculateLastDayOfYearMonth(budget.year(), month))})
                .collect(Collectors.toMap(in -> (Month) in[0], in -> (Money) in[1]));


        budget.updateAmountUsed(amountUsed);
        budgetRepository.storeBudget(budget);
    }

    private LocalDate calculateLastDayOfYearMonth(Year year, Month month) {
        return YearMonth.of(year.getValue(), month).atEndOfMonth();
    }

    private LocalDate calculateFirstDayOfYearMonth(Year year, Month month) {
        return LocalDate.of(year.getValue(), month, 1);
    }
}
