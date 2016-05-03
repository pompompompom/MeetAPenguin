package com.penguin.meetapenguin.ws.local;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * This a syncronization service broadcast receiver. It receives broadcast that the syncronization service run. Thus, it can notify the UI component that it should update itself.
 */
public class SyncronizationBroadcastReceiver {

    public static final String ENTITIES_REMOTE_UPDATED = "ENTITIES_REMOTELY UPDATED";
    private RemoteEntitiesUpdateListener mListener;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateStatus(context);
        }
    };

    private void updateStatus(Context context) {
        if (mListener != null) {
            mListener.onUpdate();
        }
    }

    public void registerContext(Context context, RemoteEntitiesUpdateListener listener) {
        this.mListener = listener;
        IntentFilter filter = new IntentFilter(SyncronizationBroadcastReceiver.ENTITIES_REMOTE_UPDATED);
        context.registerReceiver(receiver, filter);
        updateStatus(context);
    }

    public interface RemoteEntitiesUpdateListener {
        void onUpdate();
    }
}
