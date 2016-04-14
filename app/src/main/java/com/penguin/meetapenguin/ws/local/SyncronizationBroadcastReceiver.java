package com.penguin.meetapenguin.ws.local;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class SyncronizationBroadcastReceiver {

    public static final String ENTITIES_REMOTE_UPDATED = "ENTITIES_REMOTELY UPDATED";
    private RemoteEntitiesUpdateListener mListener;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO DO WHATEVER IS NECESSARY AND SENT IT TO UPDATE STATUS. CHECK IF UPDATE STATUS NEEDS ANY PARAMETER.
            //TODO CHECK IF IT IS A CONTACT OR A MESSAGE UPDATE. THIS INFORMATION SHOULD COME ON THE INTENT.
            updateStatus(context);
        }
    };

    private void updateStatus(Context context) {
        if (mListener != null) {
            mListener.onUpdate();
        }
        throw new UnsupportedOperationException("Not yet implemented");
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
