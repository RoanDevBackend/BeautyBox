package org.beautybox.mapper;

import org.beautybox.entity.Product;
import org.beautybox.entity.ProductDetail;
import org.beautybox.request.CreateProductDetailRequest;
import org.beautybox.request.CreateProductRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public abstract Product fromCreateProductRequest(CreateProductRequest request);
    public abstract ProductDetail toProductDetail(CreateProductDetailRequest request);
}
