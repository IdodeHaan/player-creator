package com.haanido.playercreator.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
