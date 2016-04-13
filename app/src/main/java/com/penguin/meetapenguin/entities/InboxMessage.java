package com.penguin.meetapenguin.entities;

/**
 * Created by Prin Oungpasuk on 4/3/2016.
 */
public class InboxMessage {

    private Contact contact;
    private String message;
    private String timeStamp;

    /**
     * default no-args constructor.
     */
    public InboxMessage(){

    }

    /**
     * Getter for contact.
     * @return Contact.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Getter for message.
     * @return String message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter for time stamp.
     * @return String time stamp.
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Setter for Contact.
     * @param contact Contact to set.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Setter for message.
     * @param message Message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Setter for time stamp.
     * @param timeStamp Time stamp to set.
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
