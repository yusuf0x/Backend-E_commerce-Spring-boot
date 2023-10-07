package com.ecommerce.app.services;

import com.ecommerce.app.exceptions.BrandNotFoundException;
import com.ecommerce.app.models.Brand;
import com.ecommerce.app.repositories.BrandRepository;
import com.ecommerce.app.utils.SlugGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final FileService fileService;

    @Autowired
    public BrandService(BrandRepository brandRepository, FileService fileService) {
        this.brandRepository = brandRepository;
        this.fileService = fileService;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElseThrow(
                () ->  new BrandNotFoundException("Brand Not Found with id = "+id)
        );
    }

    public Brand createBrand(Brand brand, MultipartFile image) throws IOException {
        String fileName = fileService.uploadImage("/brands",image);
        brand.setImage(fileName);
        brand.setSlug(SlugGenerator.generateSlug(brand.getName()));
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Long id, Brand updateBrand) {
        Brand brand = getBrandById(id);
        brand.setName( !updateBrand.getName().isEmpty() ? updateBrand.getName() : brand.getName());
        brand.setSlug(SlugGenerator.generateSlug(brand.getName()));
        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        Brand brand = getBrandById(id);
        brandRepository.delete(brand);
    }
    private void resizeBrandImage(Long brandId) throws IOException {
    }
}
