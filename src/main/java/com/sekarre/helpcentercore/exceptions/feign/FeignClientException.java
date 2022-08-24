package com.sekarre.helpcentercore.exceptions.feign;

import com.sekarre.helpcentercore.exceptions.AppRuntimeException;

public class FeignClientException extends AppRuntimeException {

    public FeignClientException(String message) {
        super(message);
    }
}
