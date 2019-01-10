package com.warehousemanagement.warehousemanagement.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 8322479396520593711L;

    private String city;
    private String street;
    private String postalCode;
    private String country;

}
