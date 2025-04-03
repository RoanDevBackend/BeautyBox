package org.beautybox.service;

import org.beautybox.request.CreateCategoryRequest;

public interface CategoryService {
    void addCategory(CreateCategoryRequest request);
    String deleteCategory(String categoryId);
}
