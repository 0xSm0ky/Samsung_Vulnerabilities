package com.sec.android.app.dexonpc.discovery;

import com.sec.android.app.dexonpc.discovery.IDiscoveryServiceCallback;

interface IDiscoveryService {
    void registerCallback(IDiscoveryServiceCallback iDiscoveryServiceCallback);

    void unregisterCallback();

    void startScan();

    void stopScan();

    boolean isScanning();

    void connect(in DeviceData deviceData);

    void disconnect(in DeviceData deviceData);

    DeviceData getCurrentDevice();
}