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
        List<Contact> contactList = DatabaseConnector.getInstance(mContext).readContact(null);
        ContactInfoController contactInfoController = new ContactInfoController(mContext);
        for (Contact contact : contactList) {
            Set<ContactInfo> contactInfoList = DatabaseConnector.getInstance(mContext).readContactInfoFromUser(contact.getId());
            contact.setContactInfoArrayList(contactInfoList);
        }
        return contactList;
    }

    public void update(Contact contact) {
        DatabaseConnector.getInstance(mContext).updateContact(contact);
        for (ContactInfo info : contact.getContactInfoArrayList()) {
            int index = DatabaseConnector.getInstance(mContext).contactInfoExist(contact.getId(), info);
            if (index != -1)
                DatabaseConnector.getInstance(mContext).updateContactInfo(index, contact.getId(), info);
            else {
                int contactInforesult = (int) DatabaseConnector.getInstance(mContext).insertContactInfo(contact.getId(), info);
                info.setId(contactInforesult);
            }
        }
    }

    public void delete(Contact contact) {

    }
}
