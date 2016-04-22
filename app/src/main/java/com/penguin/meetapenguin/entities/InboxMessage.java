package com.penguin.meetapenguin.entities;

/**
 * Created by Prin Oungpasuk on 4/3/2016.
 */
public class InboxMessage {

    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

        InboxMessage message = (InboxMessage) o;

        if (id != null ? !id.equals(message.id) : message.id != null) return false;
        return contact != null ? contact.equals(message.contact) : message.contact == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
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
