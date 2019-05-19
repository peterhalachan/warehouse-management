package com.warehousemanagement.warehousemanagement.controller;

import java.net.URI;
import java.util.Objects;

import com.warehousemanagement.warehousemanagement.assembler.DistributorAssembler;
import com.warehousemanagement.warehousemanagement.dto.DistributorDto;
import com.warehousemanagement.warehousemanagement.entity.Distributor;
import com.warehousemanagement.warehousemanagement.service.DistributorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("distributor/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DistributorController {

    private static final String DISTRIBUTOR_REQUEST_MAPPING = "distributor/";

    private DistributorService distributorService;
    private DistributorAssembler distributorAssembler;

    @PostMapping("create")
    public ResponseEntity<DistributorDto> createDistributor(@RequestBody DistributorDto distributorDto) {
        final Distributor distributor = distributorService.createDistributor(distributorDto);
        if (Objects.nonNull(distributor)) {
            final URI uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path(DISTRIBUTOR_REQUEST_MAPPING).path(distributor.getId().toString()).buildAndExpand().toUri();
            return ResponseEntity.created(uri).body(distributorDto);
        }
        return ResponseEntity.unprocessableEntity().body(distributorDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<DistributorDto> getDistributor(@PathVariable("id") Long id) {
        final Distributor distributor = distributorService.findDistributorById(id).orElse(null);
        if (Objects.nonNull(distributor)) {
            return ResponseEntity.ok(distributorAssembler.toDto(distributor));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("update")
    public ResponseEntity<DistributorDto> updateDistributor(@RequestBody DistributorDto distributorDto) {
        final Distributor updatedDistributor = distributorService.updateDistributor(distributorDto);
        if (Objects.nonNull(updatedDistributor)) {
            return ResponseEntity.ok(distributorAssembler.toDto(updatedDistributor));
        }
        return ResponseEntity.unprocessableEntity().body(distributorDto);
    }

    @DeleteMapping("{name}")
    public ResponseEntity removeDistributor(@PathVariable("name") String name) {
        distributorService.deleteDistributor(name);
        return ResponseEntity.noContent().build();
    }

}
