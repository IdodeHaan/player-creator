package com.haanido.playercreator.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PhoneNumberType {

    HOME("H"),
    WORK("W"),
    MOBILE("M");

    private final String code;


}
