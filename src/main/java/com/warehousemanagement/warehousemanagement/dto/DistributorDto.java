package com.warehousemanagement.warehousemanagement.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DistributorDto implements Serializable {

    private String name;
    private String phoneNumber;
    private String email;
    private String city;
    private String street;
    private String zipCode;
    private String country;
}
