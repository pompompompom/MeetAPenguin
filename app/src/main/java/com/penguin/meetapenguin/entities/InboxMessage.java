package com.penguin.meetapenguin.entities;

/**
 * Created by Prin Oungpasuk on 4/3/2016.
 */
public class InboxMessage {

    private Contact contact;
    private String message;
    private long timeStamp;
    private boolean isDeleted;
    private boolean isReaded;

    /**
     * default no-args constructor.
     */
    public InboxMessage() {

    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean readed) {
        isReaded = readed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InboxMessage that = (InboxMessage) o;

        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = contact != null ? contact.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    /**
     * Getter for contact.
     *
     * @return Contact.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Setter for Contact.
     *
     * @param contact Contact to set.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Getter for message.
     *
     * @return String message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for message.
     *
     * @param message Message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for time stamp.
     *
     * @return String time stamp.
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Setter for time stamp.
     *
     * @param timeStamp Time stamp to set.
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
