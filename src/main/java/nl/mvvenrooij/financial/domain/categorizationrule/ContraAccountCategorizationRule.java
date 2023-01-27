package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;

public class ContraAccountCategorizationRule extends BaseCategorizationRule {

    private final String contraAccountNumber;

    public ContraAccountCategorizationRule(final Category category, final String contraAccount) {
        super(category, (transaction) -> contraAccount.equals(transaction.contraAccountNumber()));
        this.contraAccountNumber = contraAccount;
    }

    @Override
    public Object constructorValue() {
        return contraAccountNumber;
    }
}
