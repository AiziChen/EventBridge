package org.quanye.eventbridge;

/**
 * TargetPoster
 * @Quanyec
 */
public class TargetPoster {
    private String name;
    private Object data;

    public TargetPoster() {}

    public TargetPoster(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

