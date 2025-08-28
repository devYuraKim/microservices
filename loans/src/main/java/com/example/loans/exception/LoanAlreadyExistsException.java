package com.example.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class LoanAlreadyExistsException extends RuntimeException{

    public LoanAlreadyExistsException(String message) {
        super(message);
    }

}
