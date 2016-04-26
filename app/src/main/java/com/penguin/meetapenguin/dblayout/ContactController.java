package com.penguin.meetapenguin.dblayout;

import android.content.Context;

import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Contact controller for contact CRUD.
 */
public class ContactController {

    private final Context mContext;

    public ContactController(Context context) {
        mContext = context;
    }

    public int create(Contact contact) {
        int result = (int) DatabaseConnector.getInstance(mContext).insertContact(contact);
        for (ContactInfo info : contact.getContactInfoArrayList()) {
            DatabaseConnector.getInstance(mContext).insertContactInfo(contact.getId(), info);
        }
        return result;
    }

    public Contact read(Integer contactId) {
        List<Contact> contactList = DatabaseConnector.getInstance(mContext).readContact(contactId);
        if (contactList == null || contactList.size() == 0)
            return null;
        else {
            Contact contact = contactList.get(0);
            ArrayList<ContactInfo> contactInfoList = DatabaseConnector.getInstance(mContext).readContactInfoFromUser(contactId);
            contact.setContactInfoArrayList(contactInfoList);
            return contact;
        }
    }

    public List<Contact> readAll() {
        return DatabaseConnector.getInstance(mContext).readContact(null);
    }

    public void update(Contact contact) {

    }

    public void delete(Contact contact) {

    }
}
