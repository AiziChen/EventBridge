package org.quanye.eventbridge;

import android.os.Handler;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedList;

import androidx.annotation.NonNull;

/**
 * EventBridge Project
 * The Bridge Class
 * @author Quanyec
 */
public class Bridge {
    private final static String BINDER_DEFAULT_NAME = "BINDER-DEFAULT-NAME";
    private static Bridge instance;
    private LinkedList<TargetBinder> binders = new LinkedList<>();
    private LinkedList<TargetBinder> stickyBinders = new LinkedList<>();
    private LinkedList<TargetPoster> stickyPosters = new LinkedList<>();

    private Bridge() {}

    public static synchronized Bridge getDefault() {
        if (instance == null) {
            instance = new Bridge();
            return instance;
        } else {
            return instance;
        }
    }

    /**
     * Send data to DEFAULT binders
     *
     * @param data
     */
    public void post(Object data) {
        for (TargetBinder tb : binders) {
            if (tb.getName().equals(BINDER_DEFAULT_NAME)) {
                new Handler().post(() -> {
                    tb.getBinders().get().onReceive(data);
                });
            }
        }
    }

    /**
     * Send data to the `name` binders
     *
     * @param name
     * @param data
     */
    public void post(@NonNull String name, Object data) {
        for (TargetBinder tb : binders) {
            if (tb.getName().equals(name)) {
                new Handler().post(() -> {
                    tb.getBinders()
                            .get().onReceive(data);
                });
            }
        }
    }

    /**
     * Bind binder by default name
     *
     * @param binder
     */
    public void bind(Object target, Binder binder) {
        ThreadLocal<Binder> local = new ThreadLocal<>();
        local.set(binder);
        binders.add(new TargetBinder(BINDER_DEFAULT_NAME, target, local));
    }

    /**
     * Bind binder by name
     *
     * @param name
     * @param binder
     */
    public void bind(Object target, @NonNull String name, Binder binder) {
        ThreadLocal<Binder> local = new ThreadLocal<>();
        local.set(binder);
        binders.add(new TargetBinder(name, target, local));
    }




    /**
     * Send data to the DEFAULT sticky binders
     *
     * @param data
     */
    public synchronized void postSticky(Object data) {
        stickyPosters.add(new TargetPoster(BINDER_DEFAULT_NAME, data));
        boolean flag = false;
        for (TargetBinder tb : stickyBinders) {
            if (tb.getName().equals(BINDER_DEFAULT_NAME)) {
                new Handler().post(() -> {
                    tb.getBinders()
                            .get().onReceive(data);
                });
                flag = true;
            }
        }
        if (flag) {
            stickyPosters.clear();
        }
    }

    /**
     * Send data to the `name` sticky binders
     *
     * @param name
     * @param data
     */
    public synchronized void postSticky(@NonNull String name, Object data) {
        stickyPosters.add(new TargetPoster(name, data));
        boolean flag = false;
        for (TargetBinder tb : stickyBinders) {
            if (tb.getName().equals(name)) {
                new Handler().post(() -> {
                    tb.getBinders()
                            .get().onReceive(data);
                });
                flag = true;
            }
        }
        if (flag) {
            stickyPosters.clear();
        }
    }

    /**
     * Bind sticky binder by DEFAULT-name
     *
     * @param binder
     */
    public void bindSticky(Object target, Binder binder) {
        ThreadLocal<Binder> local = new ThreadLocal<>();
        local.set(binder);
        TargetBinder tb = new TargetBinder(BINDER_DEFAULT_NAME, target, local);
        stickyBinders.add(tb);
        // 实现sticky功能
        Iterator<TargetPoster> tpitor = stickyPosters.iterator();
        while (tpitor.hasNext()) {
            TargetPoster tp = tpitor.next();
            if (tp.getName().equals(BINDER_DEFAULT_NAME)) {
                new Handler().post(() -> {
                    tb.getBinders()
                            .get().onReceive(tp.getData());
                });
            }
        }
    }

    /**
     * Bind sticky binder by `name`
     *
     * @param name
     * @param binder
     */
    public void bindSticky(Object target, @NonNull String name, Binder binder) {
        ThreadLocal<Binder> local = new ThreadLocal<>();
        local.set(binder);
        TargetBinder tb = new TargetBinder(name, target, local);
        stickyBinders.add(tb);
        // 实现sticky功能
        Iterator<TargetPoster> tpitor = stickyPosters.iterator();
        while (tpitor.hasNext()) {
            TargetPoster tp = tpitor.next();
            if (tp.getName().equals(name)) {
                new Handler().post(() -> {
                    tb.getBinders()
                            .get().onReceive(tp.getData());
                });
            }
        }
    }

    /**
     * Unregister all binders
     *
     * @param target
     */
    public void destroyBridge(Object target) {
        Iterator<TargetBinder> bitor = binders.iterator();
        while (bitor.hasNext()) {
            TargetBinder tb = bitor.next();
            if (tb.getTarget() == target) {
                bitor.remove();
            }
        }
        Iterator<TargetBinder> sbitor = stickyBinders.iterator();
        while(sbitor.hasNext()) {
            TargetBinder tb = sbitor.next();
            if (tb.getTarget() == target) {
                sbitor.remove();
            }
        }
    }

}
