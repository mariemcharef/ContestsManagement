package com.cp.Contests_management.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Catches validation errors (MethodArgumentNotValidException) from @Valid annotations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?>  handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
       // Returns HTTP 400 (Bad Request) with the error map
        ex.getBindingResult().getAllErrors()
                .forEach(error ->{
                    var fieldName=((FieldError)error).getField();
                    var errorMessage=error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //Catches Spring's ResponseStatusException
    //Returns the same status code from the exception with its reason message
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }

    //Returns HTTP 500 (Internal Server Error) with a generic message plus the exception details
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
