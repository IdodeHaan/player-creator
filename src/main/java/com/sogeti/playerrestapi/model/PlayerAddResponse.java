package com.sogeti.playerrestapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerAddResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private double ranking;
}
