package com.warehousemanagement.warehousemanagement.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = 8322479396520593711L;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STREET")
    private String street;

    @Column(name = "ZIP_CODE", length = 32)
    private String zipCode;

    @Column(name = "COUNTRY")
    private String country;

}
