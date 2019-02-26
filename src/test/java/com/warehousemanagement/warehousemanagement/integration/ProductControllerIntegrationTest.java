package com.warehousemanagement.warehousemanagement.integration;

import static com.warehousemanagement.warehousemanagement.util.ObjectSerializer.asJsonString;
import static com.warehousemanagement.warehousemanagement.util.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.warehousemanagement.warehousemanagement.WarehouseManagementApplication;
import com.warehousemanagement.warehousemanagement.assembler.ProductAssembler;
import com.warehousemanagement.warehousemanagement.dto.ProductDto;
import com.warehousemanagement.warehousemanagement.entity.Distributor;
import com.warehousemanagement.warehousemanagement.entity.Product;
import com.warehousemanagement.warehousemanagement.repository.DistributorRepository;
import com.warehousemanagement.warehousemanagement.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WarehouseManagementApplication.class })
public class ProductControllerIntegrationTest {

    private static final String PRODUCT_CONTROLLER_PATH = "/product";
    private static final String CREATE_PRODUCT = "/create";
    private static final String UPDATE_PRODUCT = "/update";
    private static final String LIST_PRODUCTS = "/all?page=%s&page-size=%s";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private ProductAssembler productAssembler;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        productRepository.deleteAll();
        distributorRepository.deleteAll();
    }

    @Test
    public void createProduct_validDto_productCreated() throws Exception {
        final ProductDto productDto = createProductDto();
        final String uri = new StringBuilder(PRODUCT_CONTROLLER_PATH).append(CREATE_PRODUCT).toString();

        final ResultActions resultActions = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content((asJsonString(productDto))));

        resultActions.andExpect(status().isCreated());
        assertResponse(resultActions, productDto);
    }

    @Test
    public void createProduct_emptyDto_unprocessableEntity() throws Exception {
        final String uri = new StringBuilder(PRODUCT_CONTROLLER_PATH).append(CREATE_PRODUCT).toString();

        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(asJsonString(new ProductDto())))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void  updateProduct_validDto_existingProductUpdated() throws Exception {
        Distributor distributor = createDistributorWithoutProducts();
        distributor = distributorRepository.save(distributor);
        final Product product = createProduct(distributor);
        productRepository.save(product);
        final ProductDto productDto = productAssembler.toDto(product);
        productDto.setDescription(null);
        final String uri = new StringBuilder(PRODUCT_CONTROLLER_PATH).append(UPDATE_PRODUCT).toString();

        final ResultActions resultActions = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(asJsonString(productDto)));

        resultActions.andExpect(status().isOk());
        assertResponse(resultActions, productDto);
    }

    @Test
    public void updateProduct_emptyDto_unprocessableEntity() throws Exception {
        final String uri = new StringBuilder(PRODUCT_CONTROLLER_PATH).append(UPDATE_PRODUCT).toString();

        mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(asJsonString(new ProductDto())))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getAll_multipleProductsInDbPresent_listsPageOfProducts() throws Exception {
        Distributor distributor = createDistributorWithoutProducts();
        distributor = distributorRepository.save(distributor);
        productRepository.saveAll(createProducts(10, distributor));
        final String uri = new StringBuilder(PRODUCT_CONTROLLER_PATH).append(String.format(LIST_PRODUCTS, 1, 2)).toString();

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    public void getAll_noProductInDb_emptyList() throws Exception {
        final String uri = new StringBuilder(PRODUCT_CONTROLLER_PATH).append(String.format(LIST_PRODUCTS, 1, 2)).toString();

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    private void assertResponse(ResultActions resultActions, ProductDto productDto) throws Exception {
        resultActions.andExpect(jsonPath("$.brandName").value(productDto.getBrandName()))
                .andExpect(jsonPath("$.productName").value(productDto.getProductName()))
                .andExpect(jsonPath("$.description").value(productDto.getDescription()))
                .andExpect(jsonPath("$.numberOfUNits").value(productDto.getNumberOfUNits()))
                .andExpect(jsonPath("$.distributorName").value(productDto.getDistributorName()));
    }

}
