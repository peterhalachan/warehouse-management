package com.warehousemanagement.warehousemanagement.assembler;

import com.warehousemanagement.warehousemanagement.dto.ProductDto;
import com.warehousemanagement.warehousemanagement.entity.Product;
import com.warehousemanagement.warehousemanagement.repository.DistributorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductAssembler extends AbstractAsembler<Product, ProductDto> {

    private DistributorRepository distributorRepository;

    @Override
    public ProductDto toDto(Product product) {
        final ProductDto productDto = new ProductDto();
        productDto.setProductName(product.getProductName());
        productDto.setBrandName(product.getBrandName());
        productDto.setDescription(product.getDescription());
        productDto.setNumberOfUNits(product.getNumberOfUNits());
        productDto.setDistributorName(product.getDistributor().getName());
        return productDto;
    }

    @Override
    public Product fromDto(ProductDto productDto) {
        final Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setBrandName(productDto.getBrandName());
        product.setDescription(productDto.getDescription());
        product.setNumberOfUNits(productDto.getNumberOfUNits());
        product.setDistributor(distributorRepository.findByName(productDto.getDistributorName()));
        return product;
    }
}
