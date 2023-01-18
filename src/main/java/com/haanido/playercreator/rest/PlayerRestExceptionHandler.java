package com.haanido.playercreator.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlayerRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PlayerErrorResponse> handleException(PlayerNotFoundException exc) {
        PlayerErrorResponse error = new PlayerErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<PlayerErrorResponse>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler
    public ResponseEntity<PlayerErrorResponse> handleException(Exception exc) {
        PlayerErrorResponse error = new PlayerErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<PlayerErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }

}
