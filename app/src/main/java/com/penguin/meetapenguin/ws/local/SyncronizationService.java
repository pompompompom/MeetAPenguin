package com.penguin.meetapenguin.ws.local;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * helper methods.
 */
public class SyncronizationService extends IntentService {
    private static final String ACTION_CONTACT_SYNCRONIZATION = "com.penguin.meetapenguin.ws.local.action.contacts";
    private static final String ACTION_MESSAGE_SYNCRONIZATION = "com.penguin.meetapenguin.ws.local.action.messages";
    private static final String EXTRA_PARAM1 = "com.penguin.meetapenguin.ws.local.extra.PARAM1";

    public SyncronizationService() {
        super("SyncronizationService");
    }

    /**
     * Starts this service to perform a contact synchronization task.
     *
     * @see IntentService
     */
    public static void startContactSyncronization(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SyncronizationService.class);
        intent.setAction(ACTION_CONTACT_SYNCRONIZATION);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    /**
     * Starts this service to perform a contact synchronization task.
     *
     * @see IntentService
     */
    public static void receiveInboxMessages(Context context, String param1) {
        Intent intent = new Intent(context, SyncronizationService.class);
        intent.setAction(ACTION_CONTACT_SYNCRONIZATION);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CONTACT_SYNCRONIZATION.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleContactSyncronization(param1);
            } else if (ACTION_MESSAGE_SYNCRONIZATION.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleMessageSyncronization(param1);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleContactSyncronization(String param1) {
        broadcastContactUpdate();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleMessageSyncronization(String param1) {
        broadcastMessageUpdate();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void broadcastContactUpdate() {
        //TODO SEND A BROADCAST TO SyncronizationBroadcastReceiver
    }

    private void broadcastMessageUpdate() {
        //TODO SEND A BROADCAST TO SyncronizationBroadcastReceiver
    }
}
