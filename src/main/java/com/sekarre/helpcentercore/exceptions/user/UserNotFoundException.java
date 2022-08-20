package com.sekarre.helpcentercore.exceptions.user;


import com.sekarre.helpcentercore.exceptions.AppRuntimeException;

public class UserNotFoundException extends AppRuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
