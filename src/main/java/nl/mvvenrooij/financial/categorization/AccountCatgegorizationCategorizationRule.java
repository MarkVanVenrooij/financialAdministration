package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;

public class AccountCatgegorizationCategorizationRule extends BaseCategorizationRule {
    public AccountCatgegorizationCategorizationRule(final Category category, final String bankAccountNumber) {
        super(category, (transaction) -> bankAccountNumber.equals(transaction.accountNumber()));
    }


}
