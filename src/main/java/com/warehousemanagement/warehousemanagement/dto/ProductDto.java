package com.warehousemanagement.warehousemanagement.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductDto implements Serializable {

    private String brandName;
    private String productName;
    private String description;
    private Integer numberOfUNits;
    private String distributorName;
}
