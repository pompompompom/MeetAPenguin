package com.penguin.meetapenguin.dblayout;

import android.content.Context;

import com.penguin.meetapenguin.entities.ContactInfo;

/**
 * Contact info controller for contactInfo CRUD.
 */
public class ContactInfoController {

    private final Context mContext;

    public ContactInfoController(Context context) {
        mContext = context;
    }

    public void create(ContactInfo contactInfo) {

    }

    public ContactInfo read() {
        return null;
    }

    public void update() {

    }

    public void delete(ContactInfo contactInfo) {
        DatabaseConnector.getInstance(mContext).deleteContactInfo(contactInfo);
    }
}
