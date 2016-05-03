package com.penguin.meetapenguin.util;

/**
 * Profile manager class.
 */

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.penguin.meetapenguin.MeetAPenguim;
import com.penguin.meetapenguin.dblayout.ContactController;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.util.entitiesHelper.ContactInfoHelper;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class holds the information of the app owner.
 * It is a profile manager to easily access the his profile info from any place
 */
public class ProfileManager {

    private static final String EXPIRATION_PREFERENCE = "EXPIRATION_PREFERENCE";
    private static final String DEFAULT_SHARING_PREFERENCE = "DEFAULT_SHARING_PREFERENCES";
    private static final String TAG = ProfileManager.class.getSimpleName();
    public static String USER_PROFILE_ID = "USER_PROFILE_ID";
    public static String PROFILE_UPDATED = "RROFILE_UPDATED";
    private static ProfileManager mInstance;
    private static Contact mContact;
    private static ContactController mContactController;
    private static ExpirationOptions mExpiration;

    private ProfileManager() {
    }

    /**
     * Singleton pattern.
     *
     * @return the object instance.
     */
    public static ProfileManager getInstance() {
        if (mInstance == null) {
            mInstance = new ProfileManager();
            mContactController = new ContactController(MeetAPenguim.getAppContext());

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
            int userId = sharedPref.getInt(USER_PROFILE_ID, 0);
            mExpiration = ExpirationOptions.values()[sharedPref.getInt(EXPIRATION_PREFERENCE, ExpirationOptions.TWO_YEARS.getIndexValue())];
            if (userId != 0)
                mContact = mContactController.read(userId);
        }
        return mInstance;
    }


    /**
     * Return the user ID
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

    /**
     * Check if the user profile is update or it need to be synced with the cloud.
     *
     * @return True is updated. False is not updated.
     */
    public boolean isProfileUpdated() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        return sharedPref.getBoolean(PROFILE_UPDATED, true);
    }

    /**
     * Set that a profile need to be synced with the cloud.
     *
     * @param status true if need to be synced. False if it is up to date.
     */
    public void setProfileOutDate(boolean status) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PROFILE_UPDATED, status);
        editor.commit();
    }

    /**
     * Ge the user contact.
     *
     * @return The contact
     */
    public Contact getContact() {
        return mContact;
    }

    /**
     * Get the default expiration data that a user choose on his settings.
     *
     * @return
     */
    public ExpirationOptions getDefaultExpiration() {
        return mExpiration;
    }

    /**
     * Set a default expiration date for the user. It use it when sharing his contact info with other users.
     *
     * @param option
     */
    public void setDefaultExpiration(ExpirationOptions option) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(EXPIRATION_PREFERENCE, option.getIndexValue());
        editor.commit();
        mExpiration = option;
    }

    /**
     * GET a list of default contact info that a user wants to share with another users.
     *
     * @return A set with the contact info.
     */
    public Set<ContactInfo> getDefaultSharingPreferences() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        String json = sharedPref.getString(DEFAULT_SHARING_PREFERENCE, "");
        Set<ContactInfo> selecteContactInfo = new LinkedHashSet<>();
        if (!json.isEmpty()) {
            try {
                Set<ContactInfo> contactInfoSet = ContactInfoHelper.fromJsonList(json);
                selecteContactInfo = contactInfoSet;
            } catch (Exception e) {
                Log.e(TAG, "getDefaultSharingPreferences: ", e);
            }
        }
        return selecteContactInfo;
    }

    /**
     * Set the list of default contact info that a user wants to share with another users.
     *
     * @param contactInfoSet The set of the contact info.
     */
    public void saveDefaultSharingPreferences(Set<ContactInfo> contactInfoSet) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        String json = ContactInfoHelper.toJson(contactInfoSet);
        editor.putString(DEFAULT_SHARING_PREFERENCE, json);
        editor.commit();
    }
}
