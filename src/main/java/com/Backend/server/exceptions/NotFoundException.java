package com.Backend.server.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(long id) {
        super("Post with id: " + id + " not found");
    }

    public NotFoundException(String message) {
        super(message);
    }

}
