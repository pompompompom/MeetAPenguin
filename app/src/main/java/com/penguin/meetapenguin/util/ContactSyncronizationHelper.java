package com.penguin.meetapenguin.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.penguin.meetapenguin.MeetAPenguim;

/**
 * Created by urbano on 4/30/16.
 */
public class ContactSyncronizationHelper {

    private static final String SYNCRONIZATION_TIME = "SYNCRONIZATION_TIME";

    public static long getLastUpdatedTime() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        return sharedPref.getLong(SYNCRONIZATION_TIME, 0);
    }

    public static void setLastUpdateTime(long timestamp) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(SYNCRONIZATION_TIME, timestamp);
        editor.commit();
    }
}
