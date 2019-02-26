package com.warehousemanagement.warehousemanagement.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.dto.ProductDto;
import com.warehousemanagement.warehousemanagement.entity.Address;
import com.warehousemanagement.warehousemanagement.entity.Distributor;
import com.warehousemanagement.warehousemanagement.entity.Product;

public abstract class TestData {

    // Distributor
    public static final String DISTRIBUTOR_NAME = "DItributor ABC";
    public static final String EMAIL = "abc@distributor.com";
    public static final String PHONE_NUMBER = "123 456 789";
    // Address
    public static final String COUNTRY = "Norway";
    public static final String CITY = "Oslo";
    public static final String STREET = "Cold";
    public static final String ZIP_CODE = "123 45";
    // Product
    public static final String PRODUCT_NAME = "R2D2";
    public static final String BRAND_NAME = "Best Robots";
    public static final String DESCRIPTION = "Best robot in the world";
    public static final Integer NUMBER_OF_UNITS = 1;

    public static DistributorDto createDistributorDto() {
        final DistributorDto distributorDto = new DistributorDto();
        distributorDto.setName(DISTRIBUTOR_NAME);
        distributorDto.setEmail(EMAIL);
        distributorDto.setPhoneNumber(PHONE_NUMBER);
        distributorDto.setCountry(COUNTRY);
        distributorDto.setCity(CITY);
        distributorDto.setStreet(STREET);
        distributorDto.setZipCode(ZIP_CODE);
        return distributorDto;
    }

    public static ProductDto createProductDto() {
        final ProductDto productDto = new ProductDto();
        productDto.setProductName(PRODUCT_NAME);
        productDto.setBrandName(BRAND_NAME);
        productDto.setDescription(DESCRIPTION);
        productDto.setNumberOfUNits(NUMBER_OF_UNITS);
        productDto.setDistributorName(DISTRIBUTOR_NAME);
        return productDto;
    }

    public static Distributor createDistributorWithId(Long id) {
        final Distributor distributor = createDistributorWithoutProducts();
        distributor.setId(id);
        return distributor;
    }

    public static Distributor createDistributorWithoutProducts() {
        final Distributor distributor = new Distributor();
        distributor.setName(DISTRIBUTOR_NAME);
        distributor.setEmail(EMAIL);
        distributor.setPhoneNumber(PHONE_NUMBER);
        distributor.setAddress(getAddress());
        return distributor;
    }

    public static Distributor createDistributor(List<Product> products) {
        final Distributor distributor = new Distributor();
        products.forEach(p -> p.setDistributor(distributor));
        distributor.setName(DISTRIBUTOR_NAME);
        distributor.setEmail(EMAIL);
        distributor.setPhoneNumber(PHONE_NUMBER);
        distributor.setAddress(getAddress());
        distributor.setProducts(products);
        return distributor;
    }

    public static Address getAddress() {
        final Address address = new Address();
        address.setCountry(COUNTRY);
        address.setCity(CITY);
        address.setStreet(STREET);
        address.setZipCode(ZIP_CODE);
        return address;
    }

    public static Product getProductWithId(Long id) {
        final Product product = createProductWIthoutDitributor();
        product.setId(id);
        return product;
    }

    public static Product createProductWIthoutDitributor() {
        final Product product = new Product();
        product.setProductName(PRODUCT_NAME);
        product.setBrandName(BRAND_NAME);
        product.setDescription(DESCRIPTION);
        product.setNumberOfUNits(NUMBER_OF_UNITS);
        return product;
    }

    public static Product createProduct(Distributor distributor) {
        final Product product = createProductWIthoutDitributor();
        distributor.setProducts(Collections.singletonList(product));
        product.setDistributor(distributor);
        return product;
    }

    public static List<Product> createProducts(int numberOfProducts, Distributor distributor) {
        final List<Product> products = new ArrayList<>(numberOfProducts);
        for (int i=0; i < numberOfProducts; i++) {
            final Product product = createProductWIthoutDitributor();
            product.setProductName(new StringBuilder(product.getProductName()).append(i).toString());
            products.add(product);
        }
        products.forEach(product -> product.setDistributor(distributor));
        distributor.setProducts(products);
        return products;
    }
}
