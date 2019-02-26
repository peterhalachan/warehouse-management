package com.warehousemanagement.warehousemanagement.service.impl;

import java.util.Objects;
import java.util.function.Function;

import com.warehousemanagement.warehousemanagement.assembler.ProductAssembler;
import com.warehousemanagement.warehousemanagement.dto.ProductDto;
import com.warehousemanagement.warehousemanagement.entity.Product;
import com.warehousemanagement.warehousemanagement.repository.ProductRepository;
import com.warehousemanagement.warehousemanagement.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

    private ProductAssembler productAssembler;
    private ProductRepository productRepository;

    @Transactional
    @Override
    public Product createProduct(ProductDto productDto) {
        if (isProductValid(productDto)) {
            final Product storedProduct = productRepository.findByBrandNameAndProductName(productDto.getBrandName(), productDto.getProductName());
            if (Objects.isNull(storedProduct)) {
                log.info("Creating new product with name {}", productDto.getProductName());
                return productRepository.save(productAssembler.fromDto(productDto));
            }
        }
        return null;
    }

    @Transactional
    @Override
    public Product updateProduct(ProductDto productDto) {
        if (isProductValid(productDto)) {
            final Product storedProduct = productRepository.findByBrandNameAndProductName(productDto.getBrandName(), productDto.getProductName());
            if (Objects.nonNull(storedProduct)) {
                final Product updatedProduct = productAssembler.fromDto(productDto);
                updatedProduct.setId(storedProduct.getId());
                log.info("Updating product {}", productDto.getProductName());
                return productRepository.save(updatedProduct);
            }
        }
        log.debug("An invalid productDto was received: {}", productDto);
        return null;
    }

    @Override
    public Page<ProductDto> findAll(Integer pageNumber, Integer pageSize) {
        log.info("Searching for products on page {} with page size {}", pageNumber, pageSize);
        final Page<Product> productPage = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return productPage.map(entityToDtoMappingFunction());
    }

    private Function<Product, ProductDto> entityToDtoMappingFunction() {
        return new Function<Product, ProductDto>() {
            @Override public ProductDto apply(Product product) {
                return productAssembler.toDto(product);
            }
        };
    }

    private boolean isProductValid(ProductDto productDto) {
        return Objects.nonNull(productDto) &&
                Objects.nonNull(productDto.getBrandName()) &&
                Objects.nonNull(productDto.getProductName());
    }
}
