# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Android Framework</td>
    </tr>
    <tr>
        <td>Library path</td>
        <td><code>/system/framework/framework.jar</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.11</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.01.04</td>
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

Oversecured found in the file `com/samsung/android/wifi/SemWifiApRestoreHelper.java` logging the password from Wi-Fi AP with 4 first characters removed:
![](04%20رئيسية/Samsung%20Vulnerabilities/106.%20Framework%20—%20Leakage%20of%20Wi-Fi%20AP%20password%20to%20logs/Oversecured_report.png)

**Proof of Concept**

```
adb logcat -s SemWifiApRestoreHelper
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
