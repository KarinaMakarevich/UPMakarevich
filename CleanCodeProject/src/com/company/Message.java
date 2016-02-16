package com.company;

public class Message {
    private String id;
    private String author;
    private Long timestamp;
    private String message;

    public Message() {
    }

    public Message(String id, String author, Long timestamp, String message) {
        this.id = id;
        this.author = author;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return getId() + " " + getAuthor() + " " + getTimestamp() + " " + getMessage();
    }
}
