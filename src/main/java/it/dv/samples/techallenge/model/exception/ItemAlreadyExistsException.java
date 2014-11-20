/*
 */
package it.dv.samples.techallenge.model.exception;

/**
 * A runtime exception for when an item already exists in a repository
 *
 * @author davidvotino
 */
public class ItemAlreadyExistsException extends RuntimeException {

    private final String type;
    private final Object id;

    public ItemAlreadyExistsException(Object id, String type) {
        super();
        this.type = type;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("You're trying to create an item of type %s (id %s), but that already exists", type, id);
    }

}
