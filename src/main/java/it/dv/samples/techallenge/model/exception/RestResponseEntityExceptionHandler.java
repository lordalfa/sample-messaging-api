/*
 */
package it.dv.samples.techallenge.model.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The Rest response exception handler, allowing to unify the message format for common errors
 * <p>
 * Just defines the custom behavious, leaves to spring MVC the rest of the exception handling
 * </p>
 *
 * @author davidvotino
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, MessageTooLongException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return build(HttpStatus.CONFLICT, ex, request);
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    protected ResponseEntity<Object> handleAlreadyExist(RuntimeException ex, WebRequest request) {
        return build(HttpStatus.CONFLICT, ex, request);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        return build(HttpStatus.NOT_FOUND, ex, request);
    }

    private ResponseEntity<Object> build(HttpStatus status, RuntimeException ex, WebRequest request) {
        int code = status.value();
        String message = ex.getMessage();
        return handleExceptionInternal(ex,
                RestError.with(code, message),
                new HttpHeaders(),
                status, request);
    }

}
