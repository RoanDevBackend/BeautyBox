package org.beautybox.service;

import org.beautybox.request.CreateBrandRequest;

public interface BrandService {
    void addBrand(CreateBrandRequest request);
    boolean deleteBrand(String brandId);
}
