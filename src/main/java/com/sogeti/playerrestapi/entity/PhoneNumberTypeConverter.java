package com.sogeti.playerrestapi.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PhoneNumberTypeConverter implements AttributeConverter<PhoneNumberType, String> {

    @Override
    public String convertToDatabaseColumn(PhoneNumberType phoneNumberType) {
        if (phoneNumberType == null) {
            return null;
        }
        return phoneNumberType.getCode();
    }

    @Override
    public PhoneNumberType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(PhoneNumberType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
