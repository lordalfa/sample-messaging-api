/*
 */
package it.dv.samples.techallenge.repositories;

import it.dv.samples.techallenge.model.Message;
import it.dv.samples.techallenge.repositories.search.MessageSearchCommand;
import java.util.List;

/**
 * Message repository interface
 *
 * @author davidvotino
 */
public interface MessageRepository {

    public final static int DEFAULT_ROW_LIMIT = 20;

    /**
     * Retrieves a message given it's id
     *
     * @param id
     * @return
     */
    public Message get(long id);

    /**
     * Creates a new message with the given author
     *
     * @param author
     * @param text
     * @return
     */
    public Message create(String author, String text);

    /**
     * Deletes a message given it's id
     *
     * @param id
     * @return
     */
    public Message delete(long id);

    /**
     * Finds all messages for a given author, optionally matching text witha search term, and optionally including messages from followed people too.
     *
     * @param command filters or a null command (all rows returned with an hard limit of DEFAULT_ROW_LIMIT)
     * @return
     */
    public List<Message> find(MessageSearchCommand command);

}
