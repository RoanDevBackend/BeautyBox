package org.beautybox.service;

import org.beautybox.request.CreateCategoryRequest;

public interface CategoryService {
    void addCategory(CreateCategoryRequest request);
    void deleteCategory(String categoryId);
}
