/*
 */
package it.dv.samples.techallenge.repositories.search;

import java.io.Serializable;

/**
 * A search command used to search for messages
 *
 * @author davidvotino
 */
public class MessageSearchCommand implements Serializable {

    public final static int DEFAULT_MAX_MESSAGES = 20;

    private String author;
    private String searchTerm;
    private boolean includeFollowed = false;
    private Integer maxMessages = DEFAULT_MAX_MESSAGES;
    private boolean orderByDateDescending = true;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean isIncludeFollowed() {
        return includeFollowed;
    }

    public void setIncludeFollowed(boolean includeFollowed) {
        this.includeFollowed = includeFollowed;
    }

    public Integer getMaxMessages() {
        return maxMessages;
    }

    public void setMaxMessages(Integer maxMessages) {
        this.maxMessages = maxMessages;
    }

    public boolean isOrderByDateDescending() {
        return orderByDateDescending;
    }

    public void setOrderByDateDescending(boolean orderByDateDescending) {
        this.orderByDateDescending = orderByDateDescending;
    }

}
