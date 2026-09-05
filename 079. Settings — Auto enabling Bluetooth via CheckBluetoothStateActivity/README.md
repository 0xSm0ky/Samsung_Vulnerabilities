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
        <td>2022.09.11</td>
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

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/079.%20Settings%20—%20Auto%20enabling%20Bluetooth%20via%20CheckBluetoothStateActivity/Oversecured_report.png)

Oversecured found an unprotected dynamically registered receiver in the Settings app in the `com/samsung/android/settings/bluetooth/CheckBluetoothStateActivity.java` file. It automatically enables Bluetooth when the attacker provides the `com.samsung.intent.action.BLUETOOTH_KEYGUARD_UNLOCK` action. The enabling also happens during the launch of this activity.

**Proof of Concept**

```java
sendBroadcast(new Intent("com.samsung.intent.action.BLUETOOTH_KEYGUARD_UNLOCK"));
```

or

```java
startActivity(new Intent("android.bluetooth.devicepicker.action.LAUNCH"));
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
