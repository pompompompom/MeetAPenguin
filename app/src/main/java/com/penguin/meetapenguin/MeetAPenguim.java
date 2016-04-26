package com.penguin.meetapenguin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.penguin.meetapenguin.dblayout.AttributeController;
import com.penguin.meetapenguin.util.AttributesHelper;

/**
 * Created by urbano on 4/26/16.
 */
public class MeetAPenguim extends Application {

    private static final String DATA_INITIALIZED = "DATABASE_INITIALIZED";
    private static Context mContext;

    public static Context getAppContext() {
        return MeetAPenguim.mContext;
    }

    public void setupDatabase() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (sharedPref.getBoolean(DATA_INITIALIZED, false)) return;

        AttributeController attributeController = new AttributeController(getApplicationContext());
        attributeController.create(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Facebook));
        attributeController.create(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Email));
        attributeController.create(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Birthday));
        attributeController.create(AttributesHelper.getAttribute(AttributesHelper.AttributeType.LinkedIn));
        attributeController.create(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Nickname));
        attributeController.create(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Location));
        attributeController.create(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Twitter));
        attributeController.create(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Phone));
        attributeController.create(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Website));
        attributeController.create(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Address));

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(DATA_INITIALIZED, true);
        editor.commit();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MeetAPenguim.mContext = getApplicationContext();
        setupDatabase();
    }
}
