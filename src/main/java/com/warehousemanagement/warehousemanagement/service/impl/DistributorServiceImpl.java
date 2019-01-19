package com.warehousemanagement.warehousemanagement.service.impl;

import com.warehousemanagement.warehousemanagement.assembler.DistributorAssembler;
import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Distributor;
import com.warehousemanagement.warehousemanagement.repository.DistributorRepository;
import com.warehousemanagement.warehousemanagement.service.DistributorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DistributorServiceImpl implements DistributorService {

    private DistributorAssembler distributorAssembler;
    private DistributorRepository distributorRepository;

    @Override
    public Distributor createDistributor(DistributorDto distributorDto) {
        final Distributor storedDistributor = distributorRepository.findByName(distributorDto.getName());
        if (storedDistributor == null) {
            return distributorRepository.save(distributorAssembler.fromDto(distributorDto));
        }
        return null;
    }

    @Override
    public Distributor updateDistributor(DistributorDto distributorDto) {
        final Distributor storedDistributor = distributorRepository.findByName(distributorDto.getName());
        if (storedDistributor != null) {
            final Distributor updatedDitributor = distributorAssembler.fromDto(distributorDto);
            updatedDitributor.setId(storedDistributor.getId());
            return distributorRepository.save(updatedDitributor);
        }
        return null;
    }

    @Override
    public void deleteDistributor(String distributorName) {
        distributorRepository.deleteByName(distributorName);
    }

}
