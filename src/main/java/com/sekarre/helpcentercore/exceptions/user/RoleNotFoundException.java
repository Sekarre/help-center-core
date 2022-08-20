package com.sekarre.helpcentercore.exceptions.user;


import com.sekarre.helpcentercore.exceptions.AppRuntimeException;

public class RoleNotFoundException extends AppRuntimeException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}
