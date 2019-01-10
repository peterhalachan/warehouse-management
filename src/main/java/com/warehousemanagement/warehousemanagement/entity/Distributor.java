package com.warehousemanagement.warehousemanagement.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Distributor implements Serializable {

    private static final long serialVersionUID = -4754530630598794194L;

    private Long id;
    private String name;
    private String phoneNumber;
    private Address address;

}
