package com.liksi.hexagonal.seminar.http;

import com.liksi.hexagonal.seminar.business.exception.InvalidRequestException;
import com.liksi.hexagonal.seminar.business.exception.NotFoundException;
import com.liksi.hexagonal.seminar.business.exception.SuggestNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

	   private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	   @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Object Not Found")  // 404
	   @ExceptionHandler(NotFoundException.class)
	   public void handleNotFound(NotFoundException exception) {
			logger.error(exception.getMessage(), exception);
	   }

	   @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Bad Request")  // 400
	   @ExceptionHandler(InvalidRequestException.class)
	   public void handleInvalidRequest(InvalidRequestException exception) {
			  logger.error(exception.getMessage(), exception);
	   }

	@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Object Not Found")  // 404
	@ExceptionHandler(SuggestNotFoundException.class)
	public ProblemDetail handleInvalidRequest(SuggestNotFoundException exception) {
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
		pd.setTitle("No seminar was found according to your constraints");
		return pd;
	}
}
