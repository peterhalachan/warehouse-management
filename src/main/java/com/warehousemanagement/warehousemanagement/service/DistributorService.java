package com.warehousemanagement.warehousemanagement.service;

import java.util.Optional;

import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Distributor;

public interface DistributorService {

    Distributor createDistributor(DistributorDto distributorDto);

    Distributor updateDistributor(DistributorDto distributorDto);

    void deleteDistributor(String distributorName);

    Optional<Distributor> findDistributorById(Long id);
}
