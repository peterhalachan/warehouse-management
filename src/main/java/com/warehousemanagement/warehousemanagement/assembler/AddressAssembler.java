package com.warehousemanagement.warehousemanagement.assembler;

import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressAssembler implements Assembler<Address, DistributorDto> {

    @Override
    public DistributorDto toDto(Address address) {
        final DistributorDto distributorDto = new DistributorDto();
        distributorDto.setCountry(address.getCountry());
        distributorDto.setCity(address.getCity());
        distributorDto.setStreet(address.getStreet());
        distributorDto.setZipCode(address.getZipCode());
        return distributorDto;
    }

    @Override
    public Address fromDto(DistributorDto distributorDto) {
        final Address address = new Address();
        address.setCity(distributorDto.getCity());
        address.setCountry(distributorDto.getCountry());
        address.setStreet(distributorDto.getStreet());
        address.setZipCode(distributorDto.getZipCode());
        return address;
    }
}
