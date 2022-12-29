package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;

public class ContraAccountCatgegorizationCategorizationRule extends BaseCategorizationRule {

    public ContraAccountCatgegorizationCategorizationRule(final Category category, final String contraAccount) {
        super(category, (transaction) -> contraAccount.equals(transaction.contraAccountNumber()));
    }
}
