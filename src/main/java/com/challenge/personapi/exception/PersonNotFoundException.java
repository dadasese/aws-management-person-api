package com.challenge.personapi.exception;

public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(String message){
        super(message);
    }
}
