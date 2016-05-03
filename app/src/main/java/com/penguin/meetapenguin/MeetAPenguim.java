package com.penguin.meetapenguin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.penguin.meetapenguin.dblayout.AttributeController;
import com.penguin.meetapenguin.dblayout.ContactController;
import com.penguin.meetapenguin.util.entitiesHelper.AttributesHelper;
import com.penguin.meetapenguin.util.testHelper.DataUtil;

/**
 * The application class where we initialize classes and setup the database.
 */
public class MeetAPenguim extends Application {

    private static final String DATA_INITIALIZED = "DATABASE_INITIALIZED";
    private static Context mContext;


    /**
     * Get a application context.
     *
     * @return The application context
     */
    public static Context getAppContext() {
        return MeetAPenguim.mContext;
    }

    /**
     * Setup the attribute entity into the database and some dummyData.
     */
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

        //DEMO DATA
        ContactController contactController = new ContactController(getAppContext());
//        contactController.create(DataUtil.mockContact());
//        contactController.create(DataUtil.mockContact2());

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
