package nl.mvvenrooij.financial.domain;

public class CategoryDoesNotExist extends RuntimeException {
    public CategoryDoesNotExist(String categoryName) {
        super(categoryName);
    }
}
