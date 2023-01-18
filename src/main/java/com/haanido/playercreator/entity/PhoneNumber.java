package com.haanido.playercreator.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "phonenumber")
@Data
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String number;
    private PhoneNumberType type;
}
