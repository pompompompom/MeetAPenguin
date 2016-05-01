package com.penguin.meetapenguin.dblayout;

import android.content.Context;

import com.penguin.meetapenguin.entities.InboxMessage;

import java.util.ArrayList;

/**
 * InboxMessage controller for InboxMessage CRUD.
 */
public class InboxMessageController {

    private final Context mContext;

    public InboxMessageController(Context context) {
        mContext = context;
    }

    public long create(InboxMessage message) {
        return DatabaseConnector.getInstance(mContext).insertInboxMessage(message);
    }

    public InboxMessage read(int cloudId) {
        return DatabaseConnector.getInstance(mContext).readInboxMessage(cloudId).get(0);
    }

    public void update(InboxMessage message) {
        DatabaseConnector.getInstance(mContext).updateInboxMessage(message);
    }

    public void delete(InboxMessage message) {

    }

    public ArrayList<InboxMessage> readAll() {
        return DatabaseConnector.getInstance(mContext).readInboxMessage(null);
    }

    public void deleteAll() {
        DatabaseConnector.getInstance(mContext).deleteAllMessages();
    }
}
