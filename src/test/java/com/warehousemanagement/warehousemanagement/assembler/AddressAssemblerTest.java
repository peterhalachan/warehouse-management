package com.warehousemanagement.warehousemanagement.assembler;

import static org.assertj.core.api.Assertions.assertThat;

import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Address;
import com.warehousemanagement.warehousemanagement.util.TestData;
import org.junit.Test;

public class AddressAssemblerTest {

    private AddressAssembler addressAssembler = new AddressAssembler();

    @Test
    public void toDto_validDistributor_distributorDto() {
        final DistributorDto distributorDto = addressAssembler.toDto(TestData.getAddress());

        assertThat(distributorDto).isNotNull();
        assertThat(distributorDto.getCountry()).isEqualTo(TestData.COUNTRY);
        assertThat(distributorDto.getCity()).isEqualTo(TestData.CITY);
        assertThat(distributorDto.getStreet()).isEqualTo(TestData.STREET);
        assertThat(distributorDto.getZipCode()).isEqualTo(TestData.ZIP_CODE);
        assertThat(distributorDto.getName()).isNull();
        assertThat(distributorDto.getEmail()).isNull();
        assertThat(distributorDto.getPhoneNumber()).isNull();
    }

    @Test
    public void fromDto_validDistributorDto_address() {
        final Address address = addressAssembler.fromDto(TestData.createDistributorDto());

        assertThat(address).isNotNull();
        assertThat(address.getCountry()).isEqualTo(TestData.COUNTRY);
        assertThat(address.getCity()).isEqualTo(TestData.CITY);
        assertThat(address.getStreet()).isEqualTo(TestData.STREET);
        assertThat(address.getZipCode()).isEqualTo(TestData.ZIP_CODE);
    }
}
