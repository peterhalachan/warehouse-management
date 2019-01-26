package com.warehousemanagement.warehousemanagement.assembler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.warehousemanagement.warehousemanagement.dto.ProductDto;
import com.warehousemanagement.warehousemanagement.entity.Product;
import com.warehousemanagement.warehousemanagement.repository.DistributorRepository;
import com.warehousemanagement.warehousemanagement.util.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductAssemblerTest {

    @Mock
    private DistributorRepository distributorRepository;

    @InjectMocks
    private ProductAssembler productAssembler;

    @Test
    public void toDto_validtProduct_productDto() {
        final ProductDto productDto = productAssembler.toDto(TestData.createProduct(TestData.createDistributorWithoutProducts()));

        assertThat(productDto).isNotNull();
        assertThat(productDto.getProductName()).isEqualTo(TestData.PRODUCT_NAME);
        assertThat(productDto.getBrandName()).isEqualTo(TestData.BRAND_NAME);
        assertThat(productDto.getDescription()).isEqualTo(TestData.DESCRIPTION);
        assertThat(productDto.getNumberOfUNits()).isEqualTo(TestData.NUMBER_OF_UNITS);
        assertThat(productDto.getDistributorName()).isEqualTo(TestData.DISTRIBUTOR_NAME);
    }

    @Test
    public void fromDto_validProductDto_product() {
        when(distributorRepository.findByName(eq(TestData.DISTRIBUTOR_NAME))).thenReturn(TestData.createDistributorWithoutProducts());

        final Product product = productAssembler.fromDto(TestData.createProductDto());

        assertThat(product).isNotNull();
        assertThat(product.getProductName()).isEqualTo(TestData.PRODUCT_NAME);
        assertThat(product.getBrandName()).isEqualTo(TestData.BRAND_NAME);
        assertThat(product.getDescription()).isEqualTo(TestData.DESCRIPTION);
        assertThat(product.getNumberOfUNits()).isEqualTo(TestData.NUMBER_OF_UNITS);
        assertThat(product.getDistributor()).isNotNull();
    }

}
