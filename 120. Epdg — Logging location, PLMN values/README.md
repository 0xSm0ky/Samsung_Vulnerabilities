# Details

<table>
    <tr>
        <td>Name</td>
        <td>Epdg</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.epdg</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.20</td>
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

Oversecured found in the Epdg app logging sensitive data such as user location and PLMN values:
![](Oversecured_report.png)

**Proof of Concept**

```
adb logcat | grep sendEpdgBigDataLog
adb logcat | grep retrieveAndSaveEpdgServerIdFromSim
adb logcat | grep LocDetector
```
