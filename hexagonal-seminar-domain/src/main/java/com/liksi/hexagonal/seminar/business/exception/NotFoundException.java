package com.liksi.hexagonal.seminar.business.exception;

public class NotFoundException extends RuntimeException {
	   public NotFoundException(String message) {
			  super(message);
	   }
}
