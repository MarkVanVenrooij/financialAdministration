package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;

public class AccountCatgegoryRule extends BaseRule {
    public AccountCatgegoryRule(final Category category, final String bankAccountNumber) {
        super(category, (transaction) -> bankAccountNumber.equals(transaction.accountNumber()));
    }
}
