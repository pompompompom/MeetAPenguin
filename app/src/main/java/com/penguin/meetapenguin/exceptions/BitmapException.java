package com.penguin.meetapenguin.exceptions;

/**
 * Bitmap exception. Problem when loading a image.
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

    public void fix() {
        android.util.Log.e("MeetAPenguin", "Please address the issue " + message);
    }
}
