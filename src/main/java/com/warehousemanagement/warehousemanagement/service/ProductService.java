package com.warehousemanagement.warehousemanagement.service;

import com.warehousemanagement.warehousemanagement.dto.ProductDto;
import com.warehousemanagement.warehousemanagement.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    Product createProduct(ProductDto productDto);

    Product updateProduct(ProductDto productDto);

    Page<Product> findAll(Integer pageNumber, Integer pageSize);

}
