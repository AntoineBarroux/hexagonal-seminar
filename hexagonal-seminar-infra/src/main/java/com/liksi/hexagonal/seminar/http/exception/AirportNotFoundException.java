package com.liksi.hexagonal.seminar.http.exception;

public class AirportNotFoundException extends RuntimeException {

    public AirportNotFoundException(String message) {
        super(message);
    }
}
