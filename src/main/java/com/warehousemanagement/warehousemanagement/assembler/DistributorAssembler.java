package com.warehousemanagement.warehousemanagement.assembler;

import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Distributor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DistributorAssembler extends AbstractAsembler<Distributor, DistributorDto> {

    private AddressAssembler addressAssembler;

    @Override
    public DistributorDto toDto(Distributor distributor) {
        final DistributorDto distributorDto = addressAssembler.toDto(distributor.getAddress());
        distributorDto.setName(distributor.getName());
        distributorDto.setEmail(distributor.getEmail());
        distributorDto.setPhoneNumber(distributor.getPhoneNumber());
        return distributorDto;
    }

    @Override public Distributor fromDto(DistributorDto distributorDto) {
        final Distributor distributor = new Distributor();
        distributor.setName(distributorDto.getName());
        distributor.setEmail(distributorDto.getEmail());
        distributor.setPhoneNumber(distributorDto.getPhoneNumber());
        distributor.setAddress(addressAssembler.fromDto(distributorDto));
        return distributor;
    }
}
