package com.sekarre.helpcentercore.exceptions.notification;

import com.sekarre.helpcentercore.exceptions.AppRuntimeException;

public class NotificationSendFailedException extends AppRuntimeException {

    public NotificationSendFailedException(String message) {
        super(message);
    }
}