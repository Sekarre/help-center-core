package com.sekarre.helpcentercore.exceptions.handler;

import com.sekarre.helpcentercore.exceptions.feign.FeignClientException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignClientErrorHandler implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String key, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            return new FeignClientException("Error with feign client: " + response);
        }
        return errorDecoder.decode(key, response);
    }
}
