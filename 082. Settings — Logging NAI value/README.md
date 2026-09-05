# Details

<table>
    <tr>
        <td>Name</td>
        <td>Settings</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.settings</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.14</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.12.06</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39904">CVE-2022-39904</a> (SVE-2022-2249)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$200</td>
    </tr>
</table>

# Description

Oversecured found the NAI value logging in the Settings app in the `com/samsung/android/settings/deviceinfo/status/UserNamePreferenceController.java` file:
![](04%20رئيسية/Samsung%20Vulnerabilities/082.%20Settings%20—%20Logging%20NAI%20value/Oversecured_report.png)

**Proof of Concept**

You need to open the About phone screen and run the command:
```
adb logcat -s UserNamePreferenceController
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
