package com.penguin.meetapenguin.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.penguin.meetapenguin.MeetAPenguim;

/**
 * This class work as a helper for the sycronization service.
 * It saves the last time that a service run.
 */
public class ContactSyncronizationHelper {

    private static final String SYNCRONIZATION_TIME = "SYNCRONIZATION_TIME";

    /**
     * Get the last time that a service run
     *
     * @return the timestamp that means when the service runs.
     */
    public static long getLastUpdatedTime() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        return sharedPref.getLong(SYNCRONIZATION_TIME, 0);
    }

    /**
     * Set the last time that a service run.
     *
     * @param timestamp the timestamp that means when the service runs.
     */
    public static void setLastUpdateTime(long timestamp) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MeetAPenguim.getAppContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(SYNCRONIZATION_TIME, timestamp);
        editor.commit();
    }
}
