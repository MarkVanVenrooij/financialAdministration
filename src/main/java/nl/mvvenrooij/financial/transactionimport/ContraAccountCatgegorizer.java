package nl.mvvenrooij.financial.transactionimport;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;

public class ContraAccountCatgegorizer implements Categorizer {
    private final String contraAccount;
    private final Category category;

    public ContraAccountCatgegorizer(final Category category, final String contraAccount) {
        this.category = category;
        this.contraAccount = contraAccount;
    }

    @Override
    public void categorize(final Transaction transaction) {
        if (transaction.contraAccountNumber().equals(contraAccount)) {
            category.addTransactions(transaction);
        }
    }
}
