package org.beautybox.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.beautybox.request.CreateProductDetailRequest;
import org.beautybox.request.CreateProductRequest;
import org.beautybox.response.ApiResponse;
import org.beautybox.service.ProductService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    final ProductService productService;

    @Operation(summary = "Thêm sản phẩm mới", security = {
            @SecurityRequirement(name = "bearerAuth")
    })
    @PostMapping("/admin-api/product")
    public ApiResponse createProduct(@ModelAttribute @Valid CreateProductRequest request){
        productService.add(request);
        return ApiResponse.success("Success");
    }

    @Operation(summary = "Thêm sản phẩm con", security = {
            @SecurityRequirement(name = "bearerAuth")
    })
    @PostMapping("/admin-api/product-detail")
    public ApiResponse createProductDetail(@ModelAttribute @Valid CreateProductDetailRequest request){
        productService.addProductDetail(request);
        return ApiResponse.success("Created product detail success");
    }

    @Operation(summary = "Lấy ra danh sách sản phẩm")
    @GetMapping("/public-api/product/search")
    public ApiResponse filterProduct(@RequestParam(required = false) String value,
                                     @RequestParam(required = false, defaultValue = "0 ") long minPrice,
                                     @RequestParam(required = false, defaultValue = "999999999") long maxPrice,
                                     @RequestParam(required = false, defaultValue = "1") long pageIndex,
                                     @RequestParam(required = false, defaultValue = "40") long pageSize,
                                     @RequestParam(required = false, defaultValue = "createdAt") String orderBy,
                                     @RequestParam(required = false, defaultValue = "ASC") String sortDirection) {
        return null;
    }

    @Operation(summary = "Lấy danh sách sản phẩm theo thể loại")
    @GetMapping("/public-api/product/category")
    public ApiResponse getProductByCategory(@RequestParam String categoryId,
                                          @RequestParam(required = false, defaultValue = "1") int pageIndex,
                                          @RequestParam(required = false, defaultValue = "40") int pageSize,
                                          @RequestParam(required = false, defaultValue = "createdAt") String orderBy,
                                          @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return ApiResponse.success("Search by category success", productService.getByCategory(categoryId, pageIndex, pageSize, orderBy, sortDirection));
    }

    @Operation(summary = "Lấy danh sách sản phẩm theo thương hiệu")
    @GetMapping("/public-api/product/brand")
    public ApiResponse getProductByBrand(@RequestParam String brandId,
                                          @RequestParam(required = false, defaultValue = "1") int pageIndex,
                                          @RequestParam(required = false, defaultValue = "40") int pageSize,
                                          @RequestParam(required = false, defaultValue = "createdAt") String orderBy,
                                          @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return ApiResponse.success("Search by brand success", productService.getByBrand(brandId, pageIndex, pageSize, orderBy, sortDirection));
    }
}
