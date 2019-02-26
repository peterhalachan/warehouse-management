package com.warehousemanagement.warehousemanagement.service.impl;

import java.util.Objects;
import java.util.Optional;

import com.warehousemanagement.warehousemanagement.assembler.DistributorAssembler;
import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Distributor;
import com.warehousemanagement.warehousemanagement.repository.DistributorRepository;
import com.warehousemanagement.warehousemanagement.service.DistributorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DistributorServiceImpl implements DistributorService {

    private DistributorAssembler distributorAssembler;
    private DistributorRepository distributorRepository;

    @Transactional
    @Override
    public Distributor createDistributor(DistributorDto distributorDto) {
        if (isDtoValid(distributorDto)) {
            final Distributor storedDistributor = distributorRepository.findByName(distributorDto.getName());
            if (Objects.isNull(storedDistributor)) {
                log.info("Creating new distributor with name {}", distributorDto.getName());
                return distributorRepository.save(distributorAssembler.fromDto(distributorDto));
            }
        }
        log.debug("Received an invalid distributorDto {}", distributorDto);
        return null;
    }

    @Transactional
    @Override
    public Distributor updateDistributor(DistributorDto distributorDto) {
        if (isDtoValid(distributorDto)) {
            final Distributor storedDistributor = distributorRepository.findByName(distributorDto.getName());
            if (Objects.nonNull(storedDistributor)) {
                final Distributor updatedDitributor = distributorAssembler.fromDto(distributorDto);
                updatedDitributor.setId(storedDistributor.getId());
                log.info("Updating distributor with name {}", distributorDto.getName());
                return distributorRepository.save(updatedDitributor);
            }
        }
        log.debug("Received an invalid distributorDto {}", distributorDto);
        return null;
    }

    @Transactional
    @Override
    public void deleteDistributor(String distributorName) {
        log.info("Removing distributor with name {}", distributorName);
        distributorRepository.deleteByName(distributorName);
    }

    @Override
    public Optional<Distributor> findDistributorById(Long id) {
        return distributorRepository.findById(id);
    }

    private boolean isDtoValid(DistributorDto distributorDto) {
        return Objects.nonNull(distributorDto) && Objects.nonNull(distributorDto.getName());
    }

}
