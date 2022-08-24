package com.sekarre.helpcentercore.exceptions.handler;

import com.sekarre.helpcentercore.exceptions.AppRuntimeException;
import com.sekarre.helpcentercore.exceptions.feign.FeignClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.sekarre.helpcentercore.util.DateUtil.getCurrentDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppRuntimeException.class)
    public ResponseEntity<ErrorMessage> handleAppRuntimeException(AppRuntimeException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(getCustomErrorMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleAuthenticationException(UsernameNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(getCustomErrorMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = FeignClientException.class)
    public ResponseEntity<ErrorMessage> handleFeignClientException(FeignClientException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(getCustomErrorMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(getCustomErrorMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(getCustomErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ErrorMessage getCustomErrorMessage(String e) {
        return ErrorMessage.builder()
                .cause(e)
                .timestamp(getCurrentDateTime())
                .build();
    }
}
