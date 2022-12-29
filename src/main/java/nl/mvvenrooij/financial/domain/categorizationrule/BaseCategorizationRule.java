package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;

import java.util.Objects;
import java.util.function.Function;

public abstract class BaseCategorizationRule implements CategorizationRule {
    private final Category categoryToAssign;
    private final Function<Transaction, Boolean> functionToApply;

    public BaseCategorizationRule(final Category categoryToAssign, final Function<Transaction, Boolean> functionToApply) {
        this.categoryToAssign = Objects.requireNonNull(categoryToAssign);
        this.functionToApply = Objects.requireNonNull(functionToApply);
    }

    @Override
    public boolean categorize(final Transaction transaction) {
        if (functionToApply.apply(transaction)) {
            categoryToAssign.addTransactions(transaction);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseCategorizationRule that)) return false;
        return categoryToAssign.equals(that.categoryToAssign) && functionToApply.equals(that.functionToApply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryToAssign, functionToApply);
    }

    @Override
    public boolean matches(final Transaction transaction) {
        return functionToApply.apply(transaction);
    }

    @Override
    public Category category() {
        return categoryToAssign;
    }
}
