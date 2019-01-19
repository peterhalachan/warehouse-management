package com.warehousemanagement.warehousemanagement.service.impl;

import com.warehousemanagement.warehousemanagement.assembler.ProductAssembler;
import com.warehousemanagement.warehousemanagement.dto.ProductDto;
import com.warehousemanagement.warehousemanagement.entity.Product;
import com.warehousemanagement.warehousemanagement.repository.ProductRepository;
import com.warehousemanagement.warehousemanagement.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

    private ProductAssembler productAssembler;
    private ProductRepository productRepository;

    @Override
    public Product createProduct(ProductDto productDto) {
        final Product storedProduct = productRepository.findByBrandNameAndProductName(productDto.getBrandName(), productDto.getProductName());
        if (storedProduct == null) {
            return productRepository.save(productAssembler.fromDto(productDto));
        }
        return null;
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        final Product storedProduct = productRepository.findByBrandNameAndProductName(productDto.getBrandName(), productDto.getProductName());
        if (storedProduct != null) {
            final Product updatedProduct = productAssembler.fromDto(productDto);
            updatedProduct.setId(storedProduct.getId());
            return productRepository.save(updatedProduct);
        }
        return null;
    }

    @Override
    public Page<Product> findAll(Integer pageNumber, Integer pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
