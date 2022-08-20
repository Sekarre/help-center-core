package com.sekarre.helpcentercore.exceptions.issue;


import com.sekarre.helpcentercore.exceptions.AppRuntimeException;

public abstract class AppIssueException extends AppRuntimeException {
    public AppIssueException(String message) {
        super(message);
    }
}
