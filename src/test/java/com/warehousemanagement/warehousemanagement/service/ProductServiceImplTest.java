package com.warehousemanagement.warehousemanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.warehousemanagement.warehousemanagement.assembler.ProductAssembler;
import com.warehousemanagement.warehousemanagement.dto.ProductDto;
import com.warehousemanagement.warehousemanagement.entity.Product;
import com.warehousemanagement.warehousemanagement.repository.ProductRepository;
import com.warehousemanagement.warehousemanagement.service.impl.ProductServiceImpl;
import com.warehousemanagement.warehousemanagement.util.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductAssembler productAssembler;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void createProduct_nullProductDto_null() {
        assertThat(productService.createProduct(null)).isNull();
    }

    @Test
    public void createProduct_notStoredProductDto_storedProduct() {
        final Product testProduct = TestData.getProductWithId(1L);
        when(productRepository.findByBrandNameAndProductName(anyString(), anyString())).thenReturn(null);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        when(productAssembler.fromDto(any(ProductDto.class))).thenReturn(testProduct);

        final Product product = productService.createProduct(TestData.createProductDto());

        assertThat(product).isNotNull();
    }

    @Test
    public void createProduct_storedProductDto_null() {
        when(productRepository.findByBrandNameAndProductName(anyString(), anyString())).thenReturn(TestData.getProductWithId(1L));

        final Product product = productService.createProduct(TestData.createProductDto());

        assertThat(product).isNull();
    }

    @Test
    public void updateProduct_nullProductDto_null() {
        assertThat(productService.updateProduct(null)).isNull();
    }

    @Test
    public void updateProduct_notStoredProductDto_null() {
        when(productRepository.findByBrandNameAndProductName(anyString(), anyString())).thenReturn(null);

        final Product product = productService.createProduct(TestData.createProductDto());

        assertThat(product).isNull();
    }

    @Test
    public void updateProduct_storedProductDto_updatedProduct() {
        final Product testProduct = TestData.getProductWithId(1L);
        when(productRepository.findByBrandNameAndProductName(anyString(), anyString())).thenReturn(TestData.createProductWIthoutDitributor());
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        when(productAssembler.fromDto(any(ProductDto.class))).thenReturn(testProduct);

        final Product product = productService.updateProduct(TestData.createProductDto());

        assertThat(product).isNotNull();
    }

}
