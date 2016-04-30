package com.penguin.meetapenguin.util;

/**
 * Created by urbano on 4/22/16.
 */

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.penguin.meetapenguin.MeetAPenguim;
import com.penguin.meetapenguin.dblayout.ContactController;
import com.penguin.meetapenguin.entities.Contact;

/**
 * This class holds the information of the app owner.
 * It is a profile manager to easily access the his profile info from any place
 */
public class ProfileManager {

    public static String USER_PROFILE_ID = "USER_PROFILE_ID";
    public static String PROFILE_UPDATED = "RROFILE_UPDATED";
    private static ProfileManager mInstance;
    private static Contact mContact;
    private static ContactController mContactController;

    private ProfileManager() {
    }

    public static ProfileManager getInstance() {
        if (mInstance == null) {
            mInstance = new ProfileManager();
            mContactController = new ContactController(MeetAPenguim.getAppContext());

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
            int userId = sharedPref.getInt(USER_PROFILE_ID, 0);
            if (userId != 0)
                mContact = mContactController.read(userId);
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
        if (mContact != null) {
            mContactController.update(contact);
        } else {
            int result = mContactController.create(contact);

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(USER_PROFILE_ID, result);
            editor.commit();
        }
        mContact = contact;
    }

    public boolean isProfileUpdated() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        return sharedPref.getBoolean(PROFILE_UPDATED, true);
    }

    public void setProfileOutDate(boolean status) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PROFILE_UPDATED, status);
        editor.commit();
    }

    public Contact getContact() {
        return mContact;
    }
}
