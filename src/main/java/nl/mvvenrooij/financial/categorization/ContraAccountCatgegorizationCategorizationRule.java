package nl.mvvenrooij.financial.categorization;

import nl.mvvenrooij.financial.domain.Category;

public class ContraAccountCatgegorizationCategorizationRule extends BaseCategorizationRule {

    public ContraAccountCatgegorizationCategorizationRule(final Category category, final String contraAccount) {
        super(category, (transaction) -> contraAccount.equals(transaction.contraAccountNumber()));
    }
}
