package com.sogeti.playerrestapi.model;

import com.sogeti.playerrestapi.entity.PhoneNumberType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneNumberResponse {

    private String number;
    private PhoneNumberType type;
}
