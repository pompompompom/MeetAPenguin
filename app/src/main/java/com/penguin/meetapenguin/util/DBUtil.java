package com.penguin.meetapenguin.util;

/**
 * Database utilities
 */
public class DBUtil {

    // database name
    public static final String DATABASE_NAME = "MeetAPenguin";

    public static final String DROP_TABLE_ATTRIBUTE = "DROP TABLE IF EXISTS Attribute;";

    public static final String DROP_TABLE_CONTACT = "DROP TABLE IF EXISTS Contact;";

    public static final String DROP_TABLE_CONTACT_INFO = "DROP TABLE IF EXISTS ContactInfo;";

    public static final String DROP_TABLE_INBOX_MESSAGE = "DROP TABLE IF EXISTS InboxMessage;";

    public static final String CREATE_TABLE_ATTRIBUTE = "CREATE TABLE IF NOT EXISTS Attribute "
            + "(id INTEGER primary key,"
            + "name TEXT NOT NULL,"
            + "iconPath TEXT NOT NULL);";

    public static final String CREATE_TABLE_CONTACT = "CREATE TABLE IF NOT EXISTS Contact "
            + "(cloudId INTEGER primary key,"
            + "name TEXT NOT NULL,"
            + "description TEXT NOT NULL,"
            + "photoUrl TEXT,"
            + "expiration INTEGER NOT NULL);";

    public static final String CREATE_TABLE_CONTACT_INFO = "CREATE TABLE IF NOT EXISTS ContactInfo "
            + "(id INTEGER primary key autoincrement,"
            + "cloudId INTEGER,"
            + "attributeId INTEGER NOT NULL,"
            + "value TEXT NOT NULL,"
            + "contactId INTEGER NOT NULL,"
            + "foreign key (contactId) references contact(cloudId) ON DELETE CASCADE,"
            + "foreign key (attributeId) references attribute(id) ON DELETE CASCADE"
            + ");";

    public static final String CREATE_TABLE_INBOX_MESSAGE = "CREATE TABLE IF NOT EXISTS InboxMessage "
            + "(id INTEGER primary key autoincrement,"
            + "cloudId INTEGER,"
            + "fromContactId INTEGER NOT NULL,"
            + "toContactId INTEGER NOT NULL,"
            + "message TEXT NOT NULL,"
            + "timestamp INTEGER NOT NULL,"
            + "read BOOLEAN NOT NULL,"
            + "deleted BOOLEAN NOT NULL,"
            + "foreign key (fromContactId) references contact(cloudId) ON DELETE CASCADE,"
            + "foreign key (toContactId) references contact(cloudId) ON DELETE CASCADE"
            + ");";


    // call this in onOpen()
    public static final String TURN_ON_CONSTRAINT = "PRAGMA foreign_keys = ON;";

    public static final String SELECT_ALL_ATTRIBUTE = "SELECT * FROM Attribute;";

    public static final String SELECT_ALL_INBOXMESSAGES = "SELECT * FROM InboxMessage;";

    public static final String SELECT_INBOXMESSAGE_USING_CLOUD_ID = "SELECT * FROM InboxMessage WHERE cloudId = ?;";

    public static final String SELECT_CONTACT = "SELECT * FROM Contact WHERE cloudId = ?;";

    public static final String SELECT_CONTACT_INFO_FROM_USER = "SELECT * FROM ContactInfo WHERE contactId = ?;";

    public static final String SELECT_ALL_CONTACT = "SELECT * FROM Contact;";
}
