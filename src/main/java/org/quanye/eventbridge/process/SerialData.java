package org.quanye.eventbridge.process;

import java.io.Serializable;

public class SerialData implements Serializable {

    private Object data;

    public SerialData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
