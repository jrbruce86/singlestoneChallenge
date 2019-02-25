package singlestone.petstore.exception;

import singlestone.petstore.model.ErrorType;

public class ClientFacingException extends Exception {

    private final ErrorType errorType;


    public ClientFacingException(final String description, final ErrorType errorType, final Throwable cause) {
        super(description, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
