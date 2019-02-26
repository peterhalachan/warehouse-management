package com.warehousemanagement.warehousemanagement.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Entity
@Table(name = "DISTRIBUTOR")
public class Distributor implements Serializable {

    private static final long serialVersionUID = -4754530630598794194L;
    private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "PHONE_NUMBER", length = 50)
    private String phoneNumber;

    @Column(name = "EMAIL")
    @Pattern(regexp = EMAIL_REGEX)
    private String email;

    @Embedded
    private Address address;

    @OneToMany
    @JoinColumn(name = "DISTRIBUTOR_ID")
    private List<Product> products;

}
