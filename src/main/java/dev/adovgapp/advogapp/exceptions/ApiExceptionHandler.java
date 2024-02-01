package dev.adovgapp.advogapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus badRequest = e.getHttpStatus();
        ApiException apiException = new ApiException(
                badRequest.value(),
                badRequest,
                e.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
                );

        return new ResponseEntity<>(apiException,badRequest);

    }
}
