package org.quanye.eventbridge.process;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import org.quanye.eventbridge.bridger.Binder;
import org.quanye.eventbridge.ProcessBridge;

public class MReceiver extends BroadcastReceiver {
    public final static String MRECEIVE_DATA = "MRECEIVER_DATA";
    public final static String MRECEIVE_BINDER = "MRECEIVE_BINDER";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null
                && intent.getAction().equals(ProcessBridge.BINDER_DEFAULT_ACTION)) {
            intent.setComponent(new ComponentName(context, MService.class));
            context.startService(intent);
        }
    }
}
