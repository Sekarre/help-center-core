package com.sekarre.helpcentercore.exceptions.handler;

import com.sekarre.helpcentercore.exceptions.feign.FeignClientException;
import feign.RetryableException;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;

public class FeignClientRetryer implements Retryer {

    @Value("${feign.custom.client.maxAttempts: 3}")
    private Integer maxAttempts;

    @Value("${feign.custom.client.backoff: 2000}")
    private long backOff;
    private int attempt = 1;

    public FeignClientRetryer() {
    }

    public FeignClientRetryer(Integer maxAttempts, Long backOff) {
        this.maxAttempts = maxAttempts;
        this.backOff = backOff;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        if (attempt++ >= maxAttempts) {
            throw new FeignClientException("Couldn't connect to host: " + e.getMessage());
        }
        try {
            Thread.sleep(backOff);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new FeignClientRetryer(maxAttempts, backOff);
    }
}
