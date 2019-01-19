package com.warehousemanagement.warehousemanagement.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String brandName;
    private String productName;
    private String description;
    private Integer numberOfUNits;
    private String distributorName;
}
