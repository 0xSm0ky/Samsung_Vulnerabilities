# Details

<table>
    <tr>
        <td>Name</td>
        <td>MmsService</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.mms.service</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.12.03</td>
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
        <td>$460</td>
    </tr>
</table>

# Description

Oversecured found that the MmsService app logs IMSI value in the `com/android/mms/service/SendRequest.java` file:
![](04%20رئيسية/Samsung%20Vulnerabilities/138.%20MmsService%20—%20Logging%20IMSI%20value/Oversecured_report.png)

This file was created in AOSP, but patched in Samsung. The original code from AOSP doesn't log this.

**Proof of Concept**

Send an MMS message and run the command:
```
adb logcat | grep simImsi
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
