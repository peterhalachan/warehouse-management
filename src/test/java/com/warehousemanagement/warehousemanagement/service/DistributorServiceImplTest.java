package com.warehousemanagement.warehousemanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.warehousemanagement.warehousemanagement.assembler.DistributorAssembler;
import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Distributor;
import com.warehousemanagement.warehousemanagement.repository.DistributorRepository;
import com.warehousemanagement.warehousemanagement.service.impl.DistributorServiceImpl;
import com.warehousemanagement.warehousemanagement.util.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DistributorServiceImplTest {

    @Mock
    private DistributorAssembler distributorAssembler;

    @Mock
    private DistributorRepository distributorRepository;

    @InjectMocks
    private DistributorServiceImpl distributorService;

    @Test
    public void createDistributor_nullDistributorDto_null() {
        assertThat(distributorService.createDistributor(null)).isNull();
    }

    @Test
    public void createDistributor_notStoredDistributorDto_storedDistributor() {
        when(distributorRepository.findByName(anyString())).thenReturn(null);
        when(distributorRepository.save(any(Distributor.class))).thenReturn(TestData.createDistributorWithId(1L));
        when(distributorAssembler.fromDto(any(DistributorDto.class))).thenReturn(TestData.createDistributorWithoutProducts());

        final Distributor distributor = distributorService.createDistributor(TestData.createDistributorDto());

        assertThat(distributor).isNotNull();
    }

    @Test
    public void createDistributor_storedDistributorDto_null() {
        final Distributor testDistributor = TestData.createDistributorWithId(1L);
        when(distributorRepository.findByName(anyString())).thenReturn(testDistributor);

        final Distributor distributor = distributorService.createDistributor(TestData.createDistributorDto());

        assertThat(distributor).isNull();
    }

    @Test
    public void updateDistributor_nullDistributorDto_null() {
        assertThat(distributorService.updateDistributor(null)).isNull();
    }

    @Test
    public void updateDistributor_notStoredDistributorDto_null() {
        when(distributorRepository.findByName(anyString())).thenReturn(null);

        final Distributor distributor = distributorService.updateDistributor(TestData.createDistributorDto());

        assertThat(distributor).isNull();
    }

    @Test
    public void updateDistributor_storedDistributorDto_storedDistributor() {
        final Distributor testDistributor = TestData.createDistributorWithId(1L);
        when(distributorRepository.findByName(anyString())).thenReturn(testDistributor);
        when(distributorRepository.save(any(Distributor.class))).thenReturn(testDistributor);
        when(distributorAssembler.fromDto(any(DistributorDto.class))).thenReturn(TestData.createDistributorWithoutProducts());

        final Distributor distributor = distributorService.updateDistributor(TestData.createDistributorDto());

        assertThat(distributor).isNotNull();
    }

}
