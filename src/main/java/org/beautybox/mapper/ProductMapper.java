package org.beautybox.mapper;

import org.beautybox.entity.Product;
import org.beautybox.entity.ProductDetail;
import org.beautybox.repository.ImageRepository;
import org.beautybox.request.CreateProductDetailRequest;
import org.beautybox.request.CreateProductRequest;
import org.beautybox.response.ProductDetailResponse;
import org.beautybox.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    ImageRepository imageRepository;

    public abstract Product fromCreateProductRequest(CreateProductRequest request);
    public abstract ProductDetail toProductDetail(CreateProductDetailRequest request);
    @Mappings({
            @Mapping(target = "categoryId", source = "category.id"),
            @Mapping(target = "categoryName", source = "category.name"),
            @Mapping(target = "brandId", source = "brand.id"),
            @Mapping(target = "brandName", source = "brand.name"),
            @Mapping(target = "brandImgUrl", source = "brand.imgUrl"),
            @Mapping(target = "images", expression = "java(imageRepository.findByProductId(product.getId()))")
        }
    )
    public abstract ProductResponse toProductResponse(Product product);

    @Mappings({
            @Mapping(target = "image", source = "imageUrl"),
            @Mapping(target = "status", expression = "java(this.convertStatus(product.getStock()))")
    })
    public abstract ProductDetailResponse toProductDetailResponse(ProductDetail product);

    protected String convertStatus(int stock){
        if(stock > 0){
            return "Còn hàng";
        }
        return "Hết hàng";
    }
}
