package com.warehousemanagement.warehousemanagement.repository;

import com.warehousemanagement.warehousemanagement.entity.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributorRepository extends JpaRepository<Distributor, Long> {

    Distributor findByName(String name);

    void deleteByName(String name);

}
