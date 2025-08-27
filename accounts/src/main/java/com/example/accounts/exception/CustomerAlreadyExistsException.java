package com.example.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class CustomerAlreadyExistsException extends RuntimeException{

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }

}
