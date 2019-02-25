package singlestone.petstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import singlestone.petstore.exception.ClientFacingException;
import singlestone.petstore.service.ExceptionHandlingService;

@ControllerAdvice
public class ExceptionHandlingController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ExceptionHandlingService exceptionHandlingService;

    ExceptionHandlingController(final ExceptionHandlingService exceptionHandlingService) {
        this.exceptionHandlingService = exceptionHandlingService;
    }

    @ExceptionHandler(ClientFacingException.class)
    public ResponseEntity<String> handleClientFacingException(final ClientFacingException exception) {
        logger.info("Client-facing exception occurred: ", exception);
        return exceptionHandlingService.buildClientExceptionResponse(exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(final Exception exception) {
        logger.info("Exception caught by global exception handler: ", exception);
        return exceptionHandlingService.createExceptionResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
