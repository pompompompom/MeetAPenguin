package com.penguin.meetapenguin.util;

/**
 * Created by urbano on 4/22/16.
 */

import com.penguin.meetapenguin.entities.Contact;

/**
 * This class holds the information of the app owner.
 * It is a profile manager to easily access the his profile info from any place
 */
public class ProfileManager {

    private static ProfileManager mInstance;
    private Contact mContact;

    private ProfileManager() {
    }

    public static ProfileManager getInstance() {
        if (mInstance == null) {
            mInstance = new ProfileManager();
        }
        return mInstance;
    }

    /**
     * Return a generic user ID.
     *
     * @return
     */
    public Integer getUserId() {
        return mContact.getId();
    }

    public void saveContact(Contact contact) {
        mContact = contact;
    }

    public Contact getContact() {
        return mContact;
    }
}
