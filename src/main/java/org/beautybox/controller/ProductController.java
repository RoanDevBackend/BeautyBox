package org.beautybox.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.beautybox.request.CreateProductDetailRequest;
import org.beautybox.request.CreateProductRequest;
import org.beautybox.response.ApiResponse;
import org.beautybox.service.ProductService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
