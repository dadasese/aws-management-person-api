package com.challenge.personapi.exception;

public class PersonAlreadyExistsException extends RuntimeException{

    public PersonAlreadyExistsException(String message){
        super(message);
    }
}
