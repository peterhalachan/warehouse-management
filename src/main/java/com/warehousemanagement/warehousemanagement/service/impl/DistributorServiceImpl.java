package com.warehousemanagement.warehousemanagement.service.impl;

import java.util.Objects;

import com.warehousemanagement.warehousemanagement.assembler.DistributorAssembler;
import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Distributor;
import com.warehousemanagement.warehousemanagement.repository.DistributorRepository;
import com.warehousemanagement.warehousemanagement.service.DistributorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DistributorServiceImpl implements DistributorService {

    private DistributorAssembler distributorAssembler;
    private DistributorRepository distributorRepository;

    @Override
    public Distributor createDistributor(DistributorDto distributorDto) {
        if (Objects.nonNull(distributorDto)) {
            final Distributor storedDistributor = distributorRepository.findByName(distributorDto.getName());
            if (Objects.isNull(storedDistributor)) {
                return distributorRepository.save(distributorAssembler.fromDto(distributorDto));
            }
        }
        return null;
    }

    @Override
    public Distributor updateDistributor(DistributorDto distributorDto) {
        if (Objects.nonNull(distributorDto)) {
            final Distributor storedDistributor = distributorRepository.findByName(distributorDto.getName());
            if (Objects.nonNull(storedDistributor)) {
                final Distributor updatedDitributor = distributorAssembler.fromDto(distributorDto);
                updatedDitributor.setId(storedDistributor.getId());
                return distributorRepository.save(updatedDitributor);
            }
        }
        return null;
    }

    @Override
    public void deleteDistributor(String distributorName) {
        distributorRepository.deleteByName(distributorName);
    }

}
