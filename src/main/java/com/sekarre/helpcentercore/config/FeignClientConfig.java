package com.sekarre.helpcentercore.config;

import com.sekarre.helpcentercore.exceptions.handler.FeignClientErrorHandler;
import com.sekarre.helpcentercore.exceptions.handler.FeignClientRetryer;
import feign.RequestInterceptor;
import feign.Retryer;
import org.apache.http.entity.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import static com.sekarre.helpcentercore.security.UserDetailsHelper.getUserToken;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + getUserToken());
        };
    }

    @Bean
    public FeignClientErrorHandler feignClientErrorHandler() {
        return new FeignClientErrorHandler();
    }

    @Bean
    public Retryer retryer() {
        return new FeignClientRetryer();
    }
}
