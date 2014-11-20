/*
 */
package it.dv.samples.techallenge.model;

import java.io.Serializable;

/**
 * Message domain object
 *
 * @author davidvotino
 */
public class Message implements Serializable {

    public final static int MAX_LENGTH = 160;

    private Long id;
    private String author;
    private String text;
    private Long creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", author=" + author + ", text=" + text + ", creationTime=" + creationTime + '}';
    }

}
