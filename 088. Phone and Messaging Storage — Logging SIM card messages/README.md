# Details

<table>
    <tr>
        <td>Name</td>
        <td>Phone and Messaging Storage</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.providers.telephony</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.16</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.12.06</td>
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
        <td>$200</td>
    </tr>
</table>

# Description

Oversecured found in the Phone and Messaging Storage app in the file `com/android/providers/telephony/SmsProvider.java` logging messages from the SIM card:
![](04%20رئيسية/Samsung%20Vulnerabilities/088.%20Phone%20and%20Messaging%20Storage%20—%20Logging%20SIM%20card%20messages/Oversecured_report.png)

**Proof of Concept**

```
adb logcat -s "TP/SmsProvider"
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
