package org.beautybox.service;

import org.beautybox.request.CreateProductDetailRequest;
import org.beautybox.request.CreateProductRequest;

public interface ProductService {
    void add(CreateProductRequest productRequest);
    void addProductDetail(CreateProductDetailRequest productDetailRequest);
}
