package com.warehousemanagement.warehousemanagement.assembler;

import com.warehousemanagement.warehousemanagement.dto.message.ProductMessageDto;
import com.warehousemanagement.warehousemanagement.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMessageAssembler implements Assembler<Product, ProductMessageDto> {

    @Override
    public ProductMessageDto toDto(Product product) {
        ProductMessageDto dto = new ProductMessageDto();
        dto.setProductName(product.getProductName());
        dto.setBrandName(product.getBrandName());
        dto.setDistributorName(product.getDistributor().getName());
        return dto;
    }

    @Override
    public Product fromDto(ProductMessageDto productMessageDto) {
        throw new UnsupportedOperationException("Conversion of ProductMessageDto to Product is not supported");
    }
}
