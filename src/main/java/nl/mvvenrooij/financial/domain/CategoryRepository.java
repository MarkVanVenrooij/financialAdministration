package nl.mvvenrooij.financial.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CategoryRepository {
    private Map<String, Category> categories = new HashMap<>();

    public Optional<Category> findCategoryByName(final String name) {
        return Optional.ofNullable(categories.get(name));
    }

    public void storeCategory(final Category category) {
        categories.put(category.name(), category);
    }
}
