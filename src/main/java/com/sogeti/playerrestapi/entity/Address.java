package com.sogeti.playerrestapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String street;
    @Column(name = "house_number")
    private int houseNumber;
    @Column(name = "zip_code")
    private String zipcode;
    private String city;
    @Column(name = "country_code")
    private String countryCode;
}
