package org.quanye.eventbridge;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.quanye.eventbridge.bridger.Binder;
import org.quanye.eventbridge.process.MReceiver;
import org.quanye.eventbridge.process.ProcessTargetBinder;
import org.quanye.eventbridge.process.SerialData;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * EventBridge: Base on the BroadcastReceiver
 * 主要用于跨进程通信，基于BroadcastReceiver
 *
 * @author quanye
 */
public class ProcessBridge {

    public final static String BINDER_DEFAULT_ACTION = "BRIDGE-BINDER-ACTION";
    private final static String BINDER_DEFAULT_NAME = "BRIDGE-BINDER-NAME";
    private final static String BINDER_DEFAULT_STICKY_NAME = "BRIDGE-BINDER-STICKY-NAME";
    private final static String STICKY_NAME_PREFIX = "ӼӽӾӿԐ";

    private static ProcessBridge instance;
    private LinkedList<ProcessTargetBinder> receivers = new LinkedList<>();

    private ProcessBridge() {
    }

    public static ProcessBridge getDefault() {
        if (instance == null) {
            instance = new ProcessBridge();
            return instance;
        }
        return instance;
    }

    public void bind(Context ctx, Binder binder) {
        MReceiver receiver = new MReceiver();
        IntentFilter filter = new IntentFilter(BINDER_DEFAULT_ACTION);
        ctx.registerReceiver(receiver, filter);
        receivers.add(new ProcessTargetBinder(receiver, ctx, binder, BINDER_DEFAULT_NAME));
    }

    public void post(Context ctx, SerialData data) {
        for (ProcessTargetBinder rtb : receivers) {
            if (rtb.getName().equals(BINDER_DEFAULT_NAME)) {
                Intent intent = new Intent(BINDER_DEFAULT_ACTION);
                intent.putExtra(MReceiver.MRECEIVE_DATA, data);
                intent.putExtra(MReceiver.MRECEIVE_BINDER, rtb.getBinder());
                ctx.sendBroadcast(intent);
            }
        }
    }

    public void bind(Context ctx, String name, Binder binder) {
        MReceiver receiver = new MReceiver();
        IntentFilter filter = new IntentFilter(name);
        ctx.registerReceiver(receiver, filter);
        receivers.add(new ProcessTargetBinder(receiver, ctx, binder, name));
    }

    public void post(Context ctx, String name, SerialData data) {
        for (ProcessTargetBinder rtb : receivers) {
            if (rtb.getName().equals(name)) {
                Intent intent = new Intent(name);
                intent.putExtra(MReceiver.MRECEIVE_DATA, data);
                intent.putExtra(MReceiver.MRECEIVE_BINDER, rtb.getBinder());
                ctx.sendBroadcast(intent);
            }
        }
    }

    public void bindSticky(Context ctx, Binder binder) {
        MReceiver receiver = new MReceiver();
        IntentFilter filter = new IntentFilter(BINDER_DEFAULT_ACTION);
        ctx.registerReceiver(receiver, filter);
        receivers.add(new ProcessTargetBinder(receiver, ctx, binder, BINDER_DEFAULT_STICKY_NAME));
    }

    public void postSticky(Context ctx, SerialData data) {
        for (ProcessTargetBinder rtb : receivers) {
            if (rtb.getName().equals(BINDER_DEFAULT_STICKY_NAME)) {
                Intent intent = new Intent(BINDER_DEFAULT_ACTION);
                intent.putExtra(MReceiver.MRECEIVE_DATA, data);
                intent.putExtra(MReceiver.MRECEIVE_BINDER, rtb.getBinder());
                ctx.sendStickyBroadcast(intent);
            }
        }
    }

    public void bindSticky(Context ctx, String name, Binder binder) {
        name = STICKY_NAME_PREFIX + "." + name;
        MReceiver receiver = new MReceiver();
        IntentFilter filter = new IntentFilter(name);
        ctx.registerReceiver(receiver, filter);
        receivers.add(new ProcessTargetBinder(receiver, ctx, binder, name));
    }

    public void postSticky(Context ctx, String name, SerialData data) {
        name = STICKY_NAME_PREFIX + "." + name;
        for (ProcessTargetBinder rtb : receivers) {
            if (rtb.getName().equals(name)) {
                Intent intent = new Intent(name);
                intent.putExtra(MReceiver.MRECEIVE_DATA, data);
                intent.putExtra(MReceiver.MRECEIVE_BINDER, rtb.getBinder());
                ctx.sendStickyBroadcast(intent);
            }
        }
    }

    public void destroyBridge(Context ctx) {
        Iterator<ProcessTargetBinder> it = receivers.iterator();
        while (it.hasNext()) {
            ProcessTargetBinder ptb = it.next();
            if (ptb.getContext() == ctx) {
                // unregister the receiver
                ctx.unregisterReceiver(ptb.getReceiver());
                // then remove the process-target-binder
                it.remove();
            }
        }
    }
}
