package com.penguin.meetapenguin.model;

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

    public Contact getContact() {
        return contact;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
