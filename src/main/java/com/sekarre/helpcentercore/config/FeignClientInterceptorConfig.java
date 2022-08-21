package com.sekarre.helpcentercore.config;

import com.sekarre.helpcentercore.security.UserDetailsHelper;
import feign.RequestInterceptor;
import org.apache.http.entity.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.header.Header;

import static com.sekarre.helpcentercore.security.UserDetailsHelper.getUserToken;

@Configuration
public class FeignClientInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + getUserToken());
        };
    }
}
