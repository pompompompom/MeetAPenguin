package com.penguin.meetapenguin.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.penguin.meetapenguin.MeetAPenguim;
import com.penguin.meetapenguin.entities.Contact;

/**
 * Created by mitayun on 5/1/16.
 */
public class ZipCodeHelper {
    public static final String KEY_ZIP = "KEY_ZIP_CODE";
    public static final String DEFAULT_ZIP = "94043";

    public static void saveZipCodeToPrefs(Contact c) {
        String zip = c.getZipCode();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_ZIP + ":" + c.getId(), zip);
    }

    public static String getZipCodeFromPrefs(Contact c) {
        if (c.getZipCode() != null) {
            return c.getZipCode();
        }

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        String zipString = sharedPref.getString(KEY_ZIP + ":" + c.getId(), DEFAULT_ZIP);
        return zipString;
    }
}
