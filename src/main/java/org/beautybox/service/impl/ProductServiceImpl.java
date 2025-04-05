package org.beautybox.service.impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.beautybox.entity.*;
import org.beautybox.exception.BeautyBoxException;
import org.beautybox.exception.ErrorDetail;
import org.beautybox.mapper.ProductMapper;
import org.beautybox.repository.*;
import org.beautybox.request.CreateProductDetailRequest;
import org.beautybox.request.CreateProductRequest;
import org.beautybox.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final Cloudinary cloudinary;
    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;
    final BrandRepository brandRepository;
    final ImageRepository imageRepository;
    final ProductDetailRepository productDetailRepository;
    private final ProductMapper productMapper;

    @SneakyThrows
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(CreateProductRequest productRequest) {
        Brand brand = brandRepository.findById(productRequest.getBrandId()).orElseThrow(
                () -> new BeautyBoxException(ErrorDetail.ERR_BRAND_NOT_EXISTED)
        );
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                () -> new BeautyBoxException(ErrorDetail.ERR_CATEGORY_NOT_EXISTED)
        );
        List<String> imageUrls = new ArrayList<>();
        for(var item : productRequest.getImages()){
            imageUrls.add(this.getImageUrl(item));
        }
        Product product = productMapper.fromCreateProductRequest(productRequest);
        product.setBrand(brand);
        product.setCategory(category);
        productRepository.save(product);
        for(String imageUrl : imageUrls){
            Image image = new Image();
            image.setUrl(imageUrl);
            image.setProduct(product);
            imageRepository.save(image);
        }
    }

    @SneakyThrows
    private String getImageUrl(MultipartFile file) {
        try {
            var response = cloudinary.uploader().upload(file.getBytes(), Map.of());
            return response.get("url").toString();
        }catch (Exception e){
            throw new BeautyBoxException(ErrorDetail.ERR_WHILE_UPLOAD);
        }
    }

    @SneakyThrows
    @Override
    public void addProductDetail(CreateProductDetailRequest productDetailRequest) {
        Product product = productRepository.findById(productDetailRequest.getProductId()).orElseThrow(
                () -> new BeautyBoxException(ErrorDetail.ERR_PRODUCT_NOT_EXISTED)
        );
        String imageUrl = this.getImageUrl(productDetailRequest.getImage());
        ProductDetail productDetail = productMapper.toProductDetail(productDetailRequest);
        productDetail.setImageUrl(imageUrl);
        productDetail.setProduct(product);
        productDetailRepository.save(productDetail);
    }
}
