package com.penguin.meetapenguin.dblayout;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.penguin.meetapenguin.util.DBUtil;

/**
 * Database open helper class
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public DatabaseOpenHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DBUtil.DROP_TABLE_INBOX_MESSAGE);
        db.execSQL(DBUtil.DROP_TABLE_CONTACT_INFO);
        db.execSQL(DBUtil.DROP_TABLE_CONTACT);
        db.execSQL(DBUtil.DROP_TABLE_ATTRIBUTE);

        db.execSQL(DBUtil.CREATE_TABLE_ATTRIBUTE);
        db.execSQL(DBUtil.CREATE_TABLE_CONTACT);
        db.execSQL(DBUtil.CREATE_TABLE_CONTACT_INFO);
        db.execSQL(DBUtil.CREATE_TABLE_INBOX_MESSAGE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // leaves empty
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL(DBUtil.TURN_ON_CONSTRAINT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // leaves empty
    }
}