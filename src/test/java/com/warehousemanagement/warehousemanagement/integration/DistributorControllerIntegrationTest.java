package com.warehousemanagement.warehousemanagement.integration;

import static com.warehousemanagement.warehousemanagement.util.ObjectSerializer.asJsonString;
import static com.warehousemanagement.warehousemanagement.util.TestData.createDistributorDto;
import static com.warehousemanagement.warehousemanagement.util.TestData.createDistributorWithoutProducts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.warehousemanagement.warehousemanagement.WarehouseManagementApplication;
import com.warehousemanagement.warehousemanagement.assembler.DistributorAssembler;
import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Distributor;
import com.warehousemanagement.warehousemanagement.repository.DistributorRepository;
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
public class DistributorControllerIntegrationTest {

    private static final String DISTRIBUTOR_CONTROLLER_PATH = "/distributor";
    private static final String CREATE_DISTRIBUTOR = "/create";
    private static final String UPDATE_DISTRIBUTOR = "/update";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private DistributorAssembler assembler;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        distributorRepository.deleteAll();
    }

    @Test
    public void createDistributor_validDto_distributorCreated() throws Exception {
        final DistributorDto distributorDto = createDistributorDto();
        final String uri = new StringBuilder(DISTRIBUTOR_CONTROLLER_PATH).append(CREATE_DISTRIBUTOR).toString();

        final ResultActions resultActions = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(asJsonString(distributorDto)).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated());
        assertResponse(resultActions, distributorDto);
    }

    @Test
    public void createDistributor_emptyDto_unprocessableEntity() throws Exception {
        final String uri = new StringBuilder(DISTRIBUTOR_CONTROLLER_PATH).append(CREATE_DISTRIBUTOR).toString();

        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(asJsonString(new DistributorDto())).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getDistributor_distributorStoredInDb_distributorFound() throws Exception {
        final Distributor storedDistributor = distributorRepository.save(createDistributorWithoutProducts());
        final String uri = new StringBuilder(DISTRIBUTOR_CONTROLLER_PATH).append("/").append(storedDistributor.getId()).toString();

        final ResultActions resultActions = mockMvc.perform(get(uri));

        resultActions.andExpect(status().isOk());
        assertResponse(resultActions, assembler.toDto(storedDistributor));
    }

    @Test
    public void getDistributor_distributorNotPresentInDb_notFound() throws Exception {
        final String uri = new StringBuilder(DISTRIBUTOR_CONTROLLER_PATH).append("/").append(1).toString();

        mockMvc.perform(get(uri))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDistributor_validDto_distributorUpdated() throws Exception {
        final Distributor storedDistributor = createDistributorWithoutProducts();
        distributorRepository.save(storedDistributor);
        final DistributorDto updateDto = assembler.toDto(storedDistributor);
        updateDto.setEmail(null);
        final String uri = new StringBuilder(DISTRIBUTOR_CONTROLLER_PATH).append(UPDATE_DISTRIBUTOR).toString();

        final ResultActions resultActions = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(asJsonString(updateDto)));

        resultActions.andExpect(status().isOk());
        assertResponse(resultActions, updateDto);
    }

    @Test
    public void deleteDistributor_validDto_distributorDeleted() throws Exception {
        final Distributor storedDistributor = distributorRepository.save(createDistributorWithoutProducts());
        final String uri = new StringBuilder(DISTRIBUTOR_CONTROLLER_PATH).append("/").append(storedDistributor.getName()).toString();

        mockMvc.perform(delete(uri))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThat(distributorRepository.findAll()).isEmpty();
    }

    private void assertResponse(ResultActions resultActions, DistributorDto distributorDto) throws Exception {
        resultActions.andExpect(jsonPath("$.name").value(distributorDto.getName()))
                .andExpect(jsonPath("$.phoneNumber").value(distributorDto.getPhoneNumber()))
                .andExpect(jsonPath("$.email").value(distributorDto.getEmail()))
                .andExpect(jsonPath("$.city").value(distributorDto.getCity()))
                .andExpect(jsonPath("$.street").value(distributorDto.getStreet()))
                .andExpect(jsonPath("$.zipCode").value(distributorDto.getZipCode()))
                .andExpect(jsonPath("$.country").value(distributorDto.getCountry()));
    }

}
