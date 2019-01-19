package com.warehousemanagement.warehousemanagement.repository;

import com.warehousemanagement.warehousemanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByBrandNameAndProductName(String brandName, String prodctName);
}
