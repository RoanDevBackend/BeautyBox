package org.beautybox.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.beautybox.request.CreateCategoryRequest;
import org.beautybox.response.ApiResponse;
import org.beautybox.service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    final CategoryService categoryService;

    @PostMapping("/admin-api/category")
    public ApiResponse createCategory(@RequestBody @Valid CreateCategoryRequest request) {
        categoryService.addCategory(request);
        return ApiResponse.success("Create success");
    }

    @DeleteMapping("/admin-api/category/{categoryId}")
    public ApiResponse deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.success("Delete success");
    }

}
