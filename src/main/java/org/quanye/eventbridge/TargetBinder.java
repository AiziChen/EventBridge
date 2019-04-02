package org.quanye.eventbridge;

/**
 * TargetBinder
 * @Quanyec
 */
public class TargetBinder {
    private String name;
    private ThreadLocal<Binder> binders;
    // 该TargetBinder是属于哪个对象创建的
    private Object target;

    public TargetBinder(String name, Object target, ThreadLocal<Binder> binders) {
        this.name = name;
        this.target = target;
        this.binders = binders;
    }

    public TargetBinder() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ThreadLocal<Binder> getBinders() {
        return binders;
    }

    public void setBinders(ThreadLocal<Binder> binders) {
        this.binders = binders;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
