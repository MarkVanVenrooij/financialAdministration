package nl.mvvenrooij.financial.domain.categorizationrule;

import nl.mvvenrooij.financial.domain.Category;
import nl.mvvenrooij.financial.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BaseCategorizationRuleTest {
    @Test
    public void testEquals() {
        Function<Transaction, Boolean> function1 = (t) -> true;
        Function<Transaction, Boolean> function2 = (t) -> false;
        MockRule mockRule1 = new MockRule(new Category("cat1"), function1);
        MockRule mockRule2 = new MockRule(new Category("cat1"), function1);
        MockRule mockRule3 = new MockRule(new Category("cat2"), function1);
        MockRule mockRule4 = new MockRule(new Category("cat1"), function2);

        assertEquals(mockRule1, mockRule2);
        assertNotEquals(mockRule1, mockRule3);
        assertNotEquals(mockRule1, mockRule4);
    }

    @Test
    public void testHashcode() {
        Function<Transaction, Boolean> function1 = (t) -> true;
        Function<Transaction, Boolean> function2 = (t) -> false;
        MockRule mockRule1 = new MockRule(new Category("cat1"), function1);
        MockRule mockRule2 = new MockRule(new Category("cat1"), function1);
        MockRule mockRule3 = new MockRule(new Category("cat2"), function1);
        MockRule mockRule4 = new MockRule(new Category("cat1"), function2);

        assertEquals(mockRule1.hashCode(), mockRule2.hashCode());
        assertNotEquals(mockRule1.hashCode(), mockRule3.hashCode());
        assertNotEquals(mockRule1.hashCode(), mockRule4.hashCode());
    }


    static class MockRule extends BaseCategorizationRule {

        public MockRule(Category categoryToAssign, Function<Transaction, Boolean> functionToApply) {
            super(categoryToAssign, functionToApply);
        }

        @Override
        public Object constructorValue() {
            return null;
        }
    }
}
