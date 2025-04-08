package org.beautybox.service;

import org.beautybox.request.CreateProductDetailRequest;
import org.beautybox.request.CreateProductRequest;
import org.beautybox.response.PageResponse;

public interface ProductService {
    void add(CreateProductRequest productRequest);
    void addProductDetail(CreateProductDetailRequest productDetailRequest);
    PageResponse filterProduct(String value, int minPrice, int maxPrice, long pageIndex, long pageSize, String orderBy, String direction);
    PageResponse getByCategory(String categoryId, int pageIndex, int pageSize, String orderBy, String direction);
}
