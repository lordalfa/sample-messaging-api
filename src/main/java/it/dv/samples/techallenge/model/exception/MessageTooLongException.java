/*
 */
package it.dv.samples.techallenge.model.exception;

/**
 * Exception thrown when you try to inser a message which is longer than the allowed maximum length
 *
 * @author davidvotino
 */
public class MessageTooLongException extends RuntimeException {

    private final int length;
    private final int maxlength;

    public MessageTooLongException(int length, int maxlength) {
        super();
        this.length = length;
        this.maxlength = maxlength;
    }

    @Override
    public String getMessage() {
        return String.format("You're trying to post a message %d character long, when you're only allowed to use %d", length, maxlength);
    }

}
