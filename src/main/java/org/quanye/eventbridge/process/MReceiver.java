package org.quanye.eventbridge.process;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.quanye.eventbridge.bridger.Binder;
import org.quanye.eventbridge.ProcessBridge;

public class MReceiver extends BroadcastReceiver {
    public final static String MRECEIVE_DATA = "MRECEIVER_DATA";
    public final static String MRECEIVE_BINDER = "MRECEIVE_BINDER";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null
                && intent.getAction().equals(ProcessBridge.BINDER_DEFAULT_ACTION)) {
            SerialData data = (SerialData) intent.getSerializableExtra(MRECEIVE_DATA);
            Binder binder = (Binder) intent.getSerializableExtra(MRECEIVE_BINDER);
            binder.onReceive(data.getData());
        }
    }
}
