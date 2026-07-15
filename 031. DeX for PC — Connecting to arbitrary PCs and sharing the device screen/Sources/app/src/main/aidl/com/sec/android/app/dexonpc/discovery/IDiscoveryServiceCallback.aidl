package com.sec.android.app.dexonpc.discovery;

parcelable DeviceData;

interface IDiscoveryServiceCallback {
    void onDeviceAdded(in DeviceData deviceData);

    void onConnectionStateChanged(in DeviceData deviceData);

    void dismissCDD();
}