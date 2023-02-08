package com.microservices.user.service.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("Required user not found in the database");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
