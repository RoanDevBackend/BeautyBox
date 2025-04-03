package org.beautybox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.beautybox.entity.Brand;
import org.beautybox.exception.BeautyBoxException;
import org.beautybox.exception.ErrorDetail;
import org.beautybox.repository.BrandRepository;
import org.beautybox.request.CreateBrandRequest;
import org.beautybox.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    final BrandRepository brandRepository;

    @SneakyThrows
    @Override
    public void addBrand(CreateBrandRequest request) {
        if(brandRepository.existsByName(request.getName())) {
            throw new BeautyBoxException(ErrorDetail.ERR_BRAND_EXISTED);
        }
        Brand brand = new Brand();
        brand.setName(request.getName());
        brandRepository.save(brand);
    }

    @Override
    public String deleteBrand(String brandId) {
        Optional<Brand> brand = brandRepository.findById(brandId);
        if(brand.isPresent()) {
            brandRepository.delete(brand.get());
            return "success";
        }
        return "Brand does not exist";
    }
}
