package com.warehousemanagement.warehousemanagement.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 6602439111447793996L;

    private Long id;
    private String brandName;
    private String productName;
    private String description;
    private Distributor distributor;

}
