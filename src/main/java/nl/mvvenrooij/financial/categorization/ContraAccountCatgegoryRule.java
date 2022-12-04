package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;

public class ContraAccountCatgegoryRule extends BaseRule {

    public ContraAccountCatgegoryRule(final Category category, final String contraAccount) {
        super(category, (transaction) -> contraAccount.equals(transaction.contraAccountNumber()));
    }
}
