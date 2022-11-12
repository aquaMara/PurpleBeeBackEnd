package org.aquam.exception;

import org.aquam.model.response.AppResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<AppResponse> handleBadRequest(RuntimeException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<AppResponse> handleUnauthorized(RuntimeException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // 401
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<AppResponse> handleForbidden(RuntimeException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);    // 403
    }

    @ExceptionHandler({UsernameNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<AppResponse> handleNotFound(RuntimeException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler({RegistrationTimeoutException.class})
    public ResponseEntity<AppResponse> handleRequestTimeout(RuntimeException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), HttpStatus.REQUEST_TIMEOUT);
        return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT); // 408
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<AppResponse> handleConflict(RuntimeException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409
    }
}
