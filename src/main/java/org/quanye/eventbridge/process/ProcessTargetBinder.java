package org.quanye.eventbridge.process;

import android.content.Context;

import org.quanye.eventbridge.bridger.Binder;

/**
 * ProcessTargetBinder
 *
 * @author quanye
 */
public class ProcessTargetBinder {
    private MReceiver receiver;
    private Context context;
    private Binder binder;
    private String name;

    public ProcessTargetBinder() {
    }

    public ProcessTargetBinder(MReceiver receiver, Context context, Binder binder, String name) {
        this.receiver = receiver;
        this.context = context;
        this.binder = binder;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Binder getBinder() {
        return binder;
    }

    public void setBinder(Binder binder) {
        this.binder = binder;
    }

    public MReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(MReceiver receiver) {
        this.receiver = receiver;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
