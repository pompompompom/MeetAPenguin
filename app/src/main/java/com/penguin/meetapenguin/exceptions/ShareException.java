package com.penguin.meetapenguin.exceptions;

/**
 * Phone number exception
 */
public class ShareException extends Exception {
    private static final long serialVersionUID = 1L;

    private String message = "";

    public ShareException(String message) {
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

    public void fix() {
        android.util.Log.e("MeetAPenguin", "Please address the issue " + message);
    }
}
