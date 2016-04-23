package com.penguin.meetapenguin.dblayout;

import android.content.Context;

import com.penguin.meetapenguin.entities.Contact;

import java.util.List;

/**
 * Contact controller for contact CRUD.
 */
public class ContactController {

    private final Context mContext;

    public ContactController(Context context) {
        mContext = context;
    }

    public void create(Contact contact) {
        DatabaseConnector.getInstance(mContext).insertContact(contact);
    }

    public Contact read(Integer id) {
        return DatabaseConnector.getInstance(mContext).readContact(id).get(0);
    }

    public List<Contact> readAll() {
        return DatabaseConnector.getInstance(mContext).readContact(null);
    }

    public void update() {

    }

    public void delete(Contact contact) {

    }
}
