package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;

public class ContraAccountCatgegoryRule implements CategoryRule {
    private final String contraAccount;
    private final Category category;

    public ContraAccountCatgegoryRule(final Category category, final String contraAccount) {
        this.category = category;
        this.contraAccount = contraAccount;
    }

    @Override
    public boolean categorize(final Transaction transaction) {
        if (transaction.contraAccountNumber().equals(contraAccount)) {
            category.addTransactions(transaction);
            return true;
        }
        return false;
    }
}
