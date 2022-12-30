package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;

public class AccountCategorizationRule extends BaseCategorizationRule {
    public AccountCategorizationRule(final Category category, final String bankAccountNumber) {
        super(category, (transaction) -> bankAccountNumber.equals(transaction.accountNumber()));
    }


}
