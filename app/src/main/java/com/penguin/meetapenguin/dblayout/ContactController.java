package com.penguin.meetapenguin.dblayout;

import android.content.Context;

import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;

import java.util.List;
import java.util.Set;

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
            int contactInfoResult = (int) DatabaseConnector.getInstance(mContext).insertContactInfo(contact.getId(), info);
            info.setId(contactInfoResult);
        }
        return result;
    }

    public Contact read(Integer contactId) {
        List<Contact> contactList = DatabaseConnector.getInstance(mContext).readContact(contactId);
        if (contactList == null || contactList.size() == 0)
            return null;

        else {
            Contact contact = contactList.get(0);
            Set<ContactInfo> contactInfoList = DatabaseConnector.getInstance(mContext).readContactInfoFromUser(contactId);
            contact.setContactInfoArrayList(contactInfoList);
            return contact;
        }
    }

    public List<Contact> readAll() {
        return DatabaseConnector.getInstance(mContext).readContact(null);
    }

    public void update(Contact contact) {
        DatabaseConnector.getInstance(mContext).updateContact(contact);
        for (ContactInfo info : contact.getContactInfoArrayList()) {
            if (DatabaseConnector.getInstance(mContext).contactInfoExist(info.getId()))
                DatabaseConnector.getInstance(mContext).updateContactInfo(contact.getId(), info);
            else {
                int contactInforesult = (int) DatabaseConnector.getInstance(mContext).insertContactInfo(contact.getId(), info);
                info.setId(contactInforesult);
            }
        }
    }

    public void delete(Contact contact) {

    }
}
