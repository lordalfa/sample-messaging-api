/*
 */
package it.dv.samples.techallenge.model.exception;

import java.io.Serializable;

/**
 * Represents an error returned from a rest service
 *
 * @author davidvotino
 */
public class RestError implements Serializable {

    private final int code;
    private final String message;

    private RestError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Builder for managing error cache and so on
     *
     * @param code
     * @param message
     * @return
     */
    public static RestError with(int code, String message) {
        return new RestError(code, message);
    }

}
