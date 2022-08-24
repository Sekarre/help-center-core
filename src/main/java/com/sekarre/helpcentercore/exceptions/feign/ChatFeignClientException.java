package com.sekarre.helpcentercore.exceptions.feign;

public class ChatFeignClientException extends FeignClientException {

    public ChatFeignClientException(String message) {
        super(message);
    }
}
