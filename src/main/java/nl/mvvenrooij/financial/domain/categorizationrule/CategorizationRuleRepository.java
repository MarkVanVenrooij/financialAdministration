package nl.mvvenrooij.financial.domain.categorizationrule;

import java.util.ArrayList;
import java.util.List;

public class CategorizationRuleRepository {

    private ArrayList<CategorizationRule> categorizationRules = new ArrayList<>();

    public List<CategorizationRule> categorizationRules() {
        return categorizationRules;
    }

    public void add(final CategorizationRule categorizationRule) {
        categorizationRules.add(categorizationRule);
    }
}
