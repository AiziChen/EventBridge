package org.quanye.eventbridge.process;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.quanye.eventbridge.bridger.Binder;

import static org.quanye.eventbridge.process.MReceiver.MRECEIVE_BINDER;
import static org.quanye.eventbridge.process.MReceiver.MRECEIVE_DATA;

public class MService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SerialData data = (SerialData) intent.getSerializableExtra(MRECEIVE_DATA);
        Binder binder = (Binder) intent.getSerializableExtra(MRECEIVE_BINDER);
        binder.onReceive(data.getData());
        return super.onStartCommand(intent, flags, startId);
    }
}
