package com.warehousemanagement.warehousemanagement.assembler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Address;
import com.warehousemanagement.warehousemanagement.entity.Distributor;
import com.warehousemanagement.warehousemanagement.util.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DistributorAssemblerTest {

    @Mock
    private AddressAssembler addressAssembler;

    @InjectMocks
    private DistributorAssembler distributorAssembler;

    @Test
    public void toDto_validDistributor_distributorDto() {
        when(addressAssembler.toDto(any(Address.class))).thenReturn(new DistributorDto());

        final DistributorDto distributorDto = distributorAssembler.toDto(TestData.createDistributorWithoutProducts());

        assertThat(distributorDto).isNotNull();
        assertThat(distributorDto.getName()).isEqualTo(TestData.DISTRIBUTOR_NAME);
        assertThat(distributorDto.getEmail()).isEqualTo(TestData.EMAIL);
        assertThat(distributorDto.getPhoneNumber()).isEqualTo(TestData.PHONE_NUMBER);
        assertThat(distributorDto.getCountry()).isNull();
        assertThat(distributorDto.getCity()).isNull();
        assertThat(distributorDto.getStreet()).isNull();
        assertThat(distributorDto.getZipCode()).isNull();
    }

    @Test
    public void fromDto_validDistributorDto_distributor() {
        when(addressAssembler.fromDto(any(DistributorDto.class))).thenReturn(new Address());

        final Distributor distributor = distributorAssembler.fromDto(TestData.createDistributorDto());

        assertThat(distributor).isNotNull();
        assertThat(distributor.getName()).isEqualTo(TestData.DISTRIBUTOR_NAME);
        assertThat(distributor.getEmail()).isEqualTo(TestData.EMAIL);
        assertThat(distributor.getPhoneNumber()).isEqualTo(TestData.PHONE_NUMBER);
        assertThat(distributor.getAddress()).isNotNull();
    }
}
