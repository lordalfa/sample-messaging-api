/*
 */
package it.dv.samples.techallenge.model.exception;

/**
 * A marker runtime exception for when an item is expected to exist but doesn't
 *
 * @author davidvotino
 */
public class ItemNotFoundException extends RuntimeException {

    private final String type;
    private final Object id;

    public ItemNotFoundException(Object id, String type) {
        super();
        this.type = type;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("You're trying to fetch  an item of type %s (id %s), but that doesn't seem to exist!", type, id);
    }
}
