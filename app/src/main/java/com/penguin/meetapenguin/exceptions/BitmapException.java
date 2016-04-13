package com.penguin.meetapenguin.exceptions;

/**
 * Phone number exception
 */
public class BitmapException extends Exception {
    private static final long serialVersionUID = 1L;

    private String message = "";

    public BitmapException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
