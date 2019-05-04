package org.quanye.eventbridge.normal;

import org.quanye.eventbridge.bridger.Binder;

/**
 * TargetBinder
 * @Quanyec
 */
public class TargetBinder {
    private String name;
    private Binder binder;
    // 该TargetBinder是属于哪个对象创建的
    private Object target;

    public TargetBinder(String name, Object target, Binder binder) {
        this.name = name;
        this.target = target;
        this.binder = binder;
    }

    public TargetBinder() {
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

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
