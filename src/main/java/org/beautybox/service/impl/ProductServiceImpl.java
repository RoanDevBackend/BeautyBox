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
import org.beautybox.response.PageResponse;
import org.beautybox.response.ProductDetailResponse;
import org.beautybox.response.ProductResponse;
import org.beautybox.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Override
    public PageResponse<?> filterProduct(String value, int minPrice, int maxPrice, long pageIndex, long pageSize, String orderBy, String direction) {
        return null;
    }

    @Override
    public PageResponse<?> getByCategory(String categoryId, int pageIndex, int pageSize, String orderBy, String direction) {
        if(orderBy == null){
            orderBy = "createdAt";
        }
        if(direction == null){
            direction = "desc";
        }
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, orderBy);
        Page<Product> products = productRepository.getByCategory(categoryId, pageable);

        List<ProductResponse> productResponses = new ArrayList<>();

        for(Product product : products.getContent()){
            ProductResponse item = productMapper.toProductResponse(product);
            List<ProductDetail> productDetails = productDetailRepository.findByProductId(product.getId());
            item.setDetails(productDetails.stream().map(productMapper::toProductDetailResponse).toList());
            productResponses.add(item);
        }


        return PageResponse.<ProductResponse>builder()
                .pageSize(products.getSize())
                .pageIndex(products.getNumber())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .content(productResponses)
                .sortBy(new PageResponse.SortBy(orderBy, direction))
                .build();
    }
}
