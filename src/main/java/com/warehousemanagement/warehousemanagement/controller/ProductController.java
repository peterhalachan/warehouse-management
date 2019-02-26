package com.warehousemanagement.warehousemanagement.controller;

import java.net.URI;
import java.util.Objects;

import com.warehousemanagement.warehousemanagement.assembler.ProductAssembler;
import com.warehousemanagement.warehousemanagement.dto.ProductDto;
import com.warehousemanagement.warehousemanagement.entity.Product;
import com.warehousemanagement.warehousemanagement.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("product/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private static final String PRODUCT_REQUEST_MAPPING = "product/";

    private ProductService productService;
    private ProductAssembler productAssembler;

    @PostMapping("create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        final Product product = productService.createProduct(productDto);
        if (Objects.nonNull(product)) {
            final URI uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path(PRODUCT_REQUEST_MAPPING).path(product.getId().toString()).buildAndExpand().toUri();
            return ResponseEntity.created(uri).body(productDto);
        }
        return ResponseEntity.unprocessableEntity().body(productDto);
    }

    @PutMapping("update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        final Product updatedProduct = productService.updateProduct(productDto);
        if (Objects.nonNull(updatedProduct)) {
            return ResponseEntity.ok(productAssembler.toDto(updatedProduct));
        }
        return ResponseEntity.unprocessableEntity().body(productDto);
    }

    @GetMapping("all")
    public ResponseEntity<Page<ProductDto>> getAll(@RequestParam("page") Integer page, @RequestParam("page-size") Integer pageSize) {
        return ResponseEntity.ok(productService.findAll(page, pageSize));
    }

}
