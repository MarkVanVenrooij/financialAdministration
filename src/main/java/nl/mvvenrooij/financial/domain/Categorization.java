package nl.mvvenrooij.financial.domain;

import nl.mvvenrooij.financial.domain.categorizationrule.CategorizationRule;
import nl.mvvenrooij.financial.domain.categorizationrule.CategorizationRuleRepository;

import java.util.Objects;
import java.util.Optional;

public class Categorization {
    private final CategorizationRuleRepository categorizationRuleRepository;

    public Categorization(final CategorizationRuleRepository categorizationRuleRepository) {
        this.categorizationRuleRepository = Objects.requireNonNull(categorizationRuleRepository);
    }

    public Optional<Category> categorize(final Transaction transaction) {
        for (CategorizationRule rule : categorizationRuleRepository.categorizationRules()) {
            if (rule.matches(transaction)) {
                return Optional.of(rule.category());
            }
        }
        return Optional.empty();
    }
}
