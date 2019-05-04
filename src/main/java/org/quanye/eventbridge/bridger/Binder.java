package org.quanye.eventbridge.bridger;

import java.io.Serializable;

public interface Binder extends Serializable {
    void onReceive(Object data);
}
