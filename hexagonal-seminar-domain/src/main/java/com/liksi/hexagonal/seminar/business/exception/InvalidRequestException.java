package com.liksi.hexagonal.seminar.business.exception;

public class InvalidRequestException extends RuntimeException {
	   public InvalidRequestException(String message) {
			  super(message);
	   }
}
