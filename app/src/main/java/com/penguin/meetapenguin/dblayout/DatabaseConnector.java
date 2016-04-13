package com.penguin.meetapenguin.dblayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.penguin.meetapenguin.entities.Attribute;
import com.penguin.meetapenguin.util.DBUtil;

/**
 * Database utilities.
 */
public class DatabaseConnector {
    private static final String TAG = "DatabaseConnector";

    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    private static DatabaseConnector instance;

    public static DatabaseConnector getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseConnector(context);
        }
        return instance;
    }

    private DatabaseConnector(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context, DBUtil.DATABASE_NAME, null, 1);
    }

    // open the database
    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    // close the database
    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public void insertAttribute(Attribute attribute) {
        ContentValues newAttribute = new ContentValues();
        newAttribute.put("name", attribute.getName());
        newAttribute.put("iconPath", attribute.getIconPath());
        open();
        if (database != null) {
            database.insert("Attribute", null, newAttribute);
        }
        close();
    }

    public void printAllAttribute() {
        open();
        if (database != null) {
            Cursor cursor = database.rawQuery(DBUtil.SELECT_ALL_ATTRIBUTE, null);
            if (cursor.moveToFirst()) {
                do {
                    Log.d(TAG, "------- printing attribute --------");
                    int totalColumn = cursor.getColumnCount();
                    for (int i = 0; i < totalColumn; i++) {
                        Log.d(TAG, cursor.getString(i));
                    }
                } while (cursor.moveToNext());
            }
        }
    }
}
