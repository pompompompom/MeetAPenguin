package com.penguin.meetapenguin.dblayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.penguin.meetapenguin.entities.Attribute;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.InboxMessage;
import com.penguin.meetapenguin.util.DBUtil;
import com.penguin.meetapenguin.util.ProfileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Database utilities.
 */
public class DatabaseConnector {
    private static final String TAG = "DatabaseConnector";
    private static DatabaseConnector instance;
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    private DatabaseConnector(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context, DBUtil.DATABASE_NAME, null, 1);
    }

    public static DatabaseConnector getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseConnector(context);
        }
        return instance;
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

    public long insertInboxMessage(InboxMessage message) {
        ContentValues newAttribute = new ContentValues();
        newAttribute.put("cloudId", message.getCloudId());
        newAttribute.put("fromContactId", message.getContact().getId());
        newAttribute.put("toContactId", ProfileManager.getInstance().getUserId());
        newAttribute.put("message", message.getMessage());
        newAttribute.put("timestamp", message.getTimeStamp());
        newAttribute.put("read", message.isReaded());
        newAttribute.put("deleted", message.isDeleted());
        long result = -1;
        open();
        if (database != null) {
            result = database.insert("InboxMessage", null, newAttribute);
        }
        close();
        return result;
    }

    public ArrayList<InboxMessage> readInboxMessage(Integer id) {
        ArrayList<InboxMessage> inboxMessageArrayList = new ArrayList<>();
        open();
        if (database != null) {
            Cursor cursor;
            if (id == null) {
                cursor = database.rawQuery(DBUtil.SELECT_ALL_INBOXMESSAGES, null);
            } else {
                cursor = database.rawQuery(DBUtil.SELECT_INBOXMESSAGE_USING_CLOUD_ID, new String[]{String.valueOf(id)});
            }

            if (cursor.moveToFirst()) {
                do {

                    InboxMessage inboxMessage = new InboxMessage();
                    inboxMessage.setId(cursor.getInt(0));
                    inboxMessage.setCloudId(cursor.getInt(1));
                    List<Contact> contactList = readContact(cursor.getInt(2));
                    if (contactList.size() > 1) throw new InternalError();
                    Contact contact = contactList.get(0);
                    inboxMessage.setMessage(cursor.getString(4));
                    inboxMessage.setTimeStamp(cursor.getLong(5));
                    inboxMessage.setReaded(cursor.getInt(6) != 0);
                    inboxMessage.setDeleted(cursor.getInt(7) != 0);
                    inboxMessage.setContact(contact);
                    inboxMessageArrayList.add(inboxMessage);

                } while (cursor.moveToNext());
            }
        }
        return inboxMessageArrayList;
    }

    public long insertContact(Contact contact) {
        ContentValues newAttribute = new ContentValues();
        newAttribute.put("cloudId", contact.getId());
        newAttribute.put("name", contact.getName());
        newAttribute.put("description", contact.getDescription());
        newAttribute.put("expiration", contact.getExpiration());
        newAttribute.put("photoUrl", contact.getPhotoUrl());
        open();
        long result = -1;

        if (database != null) {
            result = database.insert("Contact", null, newAttribute);
        }
        close();

        return result;
    }

    public void updateInboxMessage(InboxMessage message) {
        ContentValues newAttribute = new ContentValues();
        newAttribute.put("cloudId", message.getCloudId());
        newAttribute.put("fromContactId", message.getContact().getId());
        newAttribute.put("toContactId", ProfileManager.getInstance().getUserId());
        newAttribute.put("message", message.getMessage());
        newAttribute.put("timestamp", message.getTimeStamp());
        newAttribute.put("read", message.isReaded());
        newAttribute.put("deleted", message.isDeleted());
        open();
        if (database != null) {
            long result = database.update("InboxMessage", newAttribute, "id=" + message.getId(), null);
        }
        close();
    }

    public List<Contact> readContact(Integer id) {
        ArrayList<Contact> contactList = new ArrayList<>();
        open();
        if (database != null) {
            Cursor cursor;
            if (id == null) {
                cursor = database.rawQuery(DBUtil.SELECT_ALL_CONTACT, null);
            } else {
                cursor = database.rawQuery(DBUtil.SELECT_CONTACT, new String[]{String.valueOf(id)});
            }
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    contact.setId(cursor.getInt(0));
                    contact.setName(cursor.getString(1));
                    contact.setDescription(cursor.getString(2));
                    contact.setPhotoUrl(cursor.getString(3));
                    contact.setExpiration(cursor.getInt(4));
                    contactList.add(contact);

                } while (cursor.moveToNext());
            }
        }
        return contactList;
    }
}
