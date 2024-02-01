package dev.adovgapp.advogapp.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {

        private final int statusCode;
        private final String message;
        private final HttpStatus httpStatus;
        private final ZonedDateTime timestamp;

    public ApiException(int statusCode,HttpStatus httpStatus, String message, ZonedDateTime timestamp) {
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
