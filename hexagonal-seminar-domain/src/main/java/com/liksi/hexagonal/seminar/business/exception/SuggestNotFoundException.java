package com.liksi.hexagonal.seminar.business.exception;

public class SuggestNotFoundException extends RuntimeException {

    public SuggestNotFoundException(String msg) {
        super(msg);
    }
}
