package com.sogeti.playerrestapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AddressResponse {
    private String street;
    private int houseNumber;
    private String zipcode;
    private String city;
    private String countryCode;
}
