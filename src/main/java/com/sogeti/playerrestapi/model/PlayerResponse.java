package com.sogeti.playerrestapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PlayerResponse {

    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private double ranking;
    private AddressResponse address;
    private List<PhoneNumberResponse> phoneNumbers;
}
