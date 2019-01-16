package com.warehousemanagement.warehousemanagement.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

    private static final long serialVersionUID = 6602439111447793996L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "BRAND_NAME", nullable = false)
    private String brandName;

    @NotNull
    @Size(max = 255)
    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Size(max = 500)
    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "NUMBER_OF_UNITS")
    private Integer numberOfUNits;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "DISTRIBUTOR_ID")
    private Distributor distributor;

}
