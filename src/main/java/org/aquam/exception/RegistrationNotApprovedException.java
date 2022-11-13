package org.aquam.exception;

public class RegistrationNotApprovedException extends RuntimeException {

    public RegistrationNotApprovedException(String message) {
        super(message);
    }
}
