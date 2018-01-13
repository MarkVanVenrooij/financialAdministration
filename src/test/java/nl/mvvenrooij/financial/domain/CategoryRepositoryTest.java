package nl.mvvenrooij.financial.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryRepositoryTest {

    @Test
    public void findNonExistingCategory() {
        CategoryRepository repository = new CategoryRepository();
        Optional<Category> optionalCategory = repository.findCategoryByName("category");
        assertFalse(optionalCategory.isPresent());
    }

    @Test
    public void storeAndFindCategory() {
        final String categoryName = "categoryName";
        final Category category = new Category(categoryName);
        CategoryRepository repository = new CategoryRepository();
        repository.storeCategory(category);
        final Optional<Category> categoryFound = repository.findCategoryByName(categoryName);
        assertTrue(categoryFound.isPresent());
        categoryFound.ifPresent(c -> assertEquals(categoryName, c.name()));
    }
}
