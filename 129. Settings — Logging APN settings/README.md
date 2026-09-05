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
        <td>2022.09.14</td>
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
        <td>$200</td>
    </tr>
</table>

# Description

Oversecured found that the Settings app logs sensitive APN settings data, including the password:
![](04%20رئيسية/Samsung%20Vulnerabilities/129.%20Settings%20—%20Logging%20APN%20settings/Oversecured_report.png)

**Proof of Concept**

To reproduce the vulnerability, go to `Connections` -> `Mobile networks` -> `Access Point Names` -> `Add` / `Edit` -> `Save`.

```
adb logcat -s ApnEditor
```

The result is the following log:
```
09-13 17:46:20.357 19655 19655 D ApnEditor: updateApnDataToDatabase values : mvno_type= numeric=28602 carrier_enabled=1 bearer=0 edited=1 bearer_bitmask=0 roaming_protocol=IP protocol=IP mvno_match_data= mcc=286 mnc=02 mmsc= type=default password=EVILEVIL
```

`EVILEVIL` is the entered password.

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
