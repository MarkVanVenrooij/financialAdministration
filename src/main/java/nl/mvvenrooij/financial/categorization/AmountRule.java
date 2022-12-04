package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.javamoney.moneta.Money;

import java.util.function.Function;

public class AmountRule implements CategoryRule {

    private final Category category;
    private final Function<Money, Boolean> functionToApply;

    public AmountRule(final Category category, final Function<Money, Boolean> functionToApply) {
        this.category = category;
        this.functionToApply = functionToApply;
    }

    @Override
    public boolean categorize(Transaction transaction) {
        if (functionToApply.apply(transaction.amount())) {
            category.addTransactions(transaction);
            return true;
        }
        return false;
    }
}
