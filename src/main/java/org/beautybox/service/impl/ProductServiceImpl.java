package org.beautybox.service.impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.beautybox.entity.Brand;
import org.beautybox.entity.Category;
import org.beautybox.entity.Image;
import org.beautybox.entity.Product;
import org.beautybox.exception.BeautyBoxException;
import org.beautybox.exception.ErrorDetail;
import org.beautybox.mapper.ProductMapper;
import org.beautybox.repository.BrandRepository;
import org.beautybox.repository.CategoryRepository;
import org.beautybox.repository.ImageRepository;
import org.beautybox.repository.ProductRepository;
import org.beautybox.request.CreateProductRequest;
import org.beautybox.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final Cloudinary cloudinary;
    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;
    final BrandRepository brandRepository;
    final ImageRepository imageRepository;
    private final ProductMapper productMapper;

    @SneakyThrows
    @Override
    public void add(CreateProductRequest productRequest) {
        List<String> imageUrls = new ArrayList<>();
        try{
            for(var item : productRequest.getImages()){
                var response = cloudinary.uploader().upload(item.getBytes(), Map.of());
                String url = response.get("url").toString();
                imageUrls.add(url);
            }
        }catch (Exception e){
            throw new BeautyBoxException(ErrorDetail.ERR_WHILE_UPLOAD);
        }
        Brand brand = brandRepository.findById(productRequest.getBrandId()).orElseThrow(
                () -> new BeautyBoxException(ErrorDetail.ERR_BRAND_NOT_EXISTED)
        );
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                () -> new BeautyBoxException(ErrorDetail.ERR_CATEGORY_NOT_EXISTED)
        );
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
}
