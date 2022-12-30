package nl.mvvenrooij.financial.domain;

import nl.mvvenrooij.financial.domain.categorizationrule.AccountCategorizationRule;
import nl.mvvenrooij.financial.domain.categorizationrule.CategorizationRuleRepository;
import nl.mvvenrooij.financial.domain.categorizationrule.CounterPartyCategorizationRule;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategorizationTest {

    private static final Category CATEGORY1 = new Category("CATEGORY1");
    private static final Category CATEGORY2 = new Category("CATEGORY2");
    public static final String COUNTER_PARTY = "counterParty";
    private CategorizationRuleRepository categorizationRepository;
    private Categorization classUnderTest;
    public static final String ACCOUNT = "account";
    private static final Transaction TRANSACTION = new Transaction(ACCOUNT, LocalDate.now(), "contraAccount", "counterParty", Money.zero(Monetary.getCurrency("EUR")), "description");

    @BeforeEach
    public void setup() {
        categorizationRepository = new CategorizationRuleRepository();
        classUnderTest = new Categorization(categorizationRepository);
    }

    @Test
    public void categorizeWithoutRules_empty() {
        assertTrue(classUnderTest.categorize(TRANSACTION).isEmpty());
    }

    @Test
    public void categorizeWithMatchingRules_category() {
        categorizationRepository.add(new AccountCategorizationRule(CATEGORY1, ACCOUNT));

        Optional<Category> optionalCategory = classUnderTest.categorize(TRANSACTION);
        assertTrue(optionalCategory.isPresent());
        assertEquals(CATEGORY1, optionalCategory.get());
    }

    @Test
    public void categorizeNonMatchingRules_empty() {
        categorizationRepository.add(new CounterPartyCategorizationRule(CATEGORY1, ACCOUNT));
        assertTrue(classUnderTest.categorize(TRANSACTION).isEmpty());
    }

    @Test
    public void categorize_firstRulePrevails() {
        categorizationRepository.add(new AccountCategorizationRule(CATEGORY1, ACCOUNT));
        categorizationRepository.add(new CounterPartyCategorizationRule(CATEGORY2, COUNTER_PARTY));

        Optional<Category> optionalCategory = classUnderTest.categorize(TRANSACTION);
        assertTrue(optionalCategory.isPresent());
        assertEquals(CATEGORY1, optionalCategory.get());
    }


}
