package singlestone.petstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import singlestone.petstore.exception.ClientFacingException;
import singlestone.petstore.model.ErrorType;

@Service
public class ExceptionHandlingService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ServerResponseFactoryService serverResponseFactoryService;

    ExceptionHandlingService(final ServerResponseFactoryService serverResponseFactoryService) {
        this.serverResponseFactoryService = serverResponseFactoryService;
    }

    public ClientFacingException createClientFriendlyException(final String description, final Exception cause) {
        return createClientFriendlyExceptionMain(description, ErrorType.UNKNOWN_ERROR, cause);
    }

    public ClientFacingException createClientFriendlyException(final String description, final ErrorType errorType) {
        return createClientFriendlyExceptionMain(description, errorType, null);
    }

    public ClientFacingException createClientFriendlyExceptionMain(final String description, final ErrorType errorType, final Exception cause) {
        return new ClientFacingException(description, errorType, cause);
    }

    public ResponseEntity buildClientExceptionResponse(final ClientFacingException exception) {
        final ErrorType errorType = exception.getErrorType();
        switch(errorType) {
            case NOT_FOUND:
                return createClientExceptionResponse(exception, HttpStatus.NOT_FOUND);
            case BAD_REQUEST:
                return createClientExceptionResponse(exception, HttpStatus.BAD_REQUEST);
            case UNKNOWN_ERROR:
                return createClientExceptionResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                logger.warn("Unsupported error type, {}, passed into global exception handler.", errorType);
                return createExceptionResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity createExceptionResponse(final Exception exception, final HttpStatus httpStatus) {
        return serverResponseFactoryService.createServerResponse(httpStatus, "Unknown server error", exception);
    }

    private ResponseEntity createClientExceptionResponse(final Exception exception, final HttpStatus httpStatus) {
        return serverResponseFactoryService.createServerResponse(httpStatus, exception.getMessage(), exception);
    }

}
