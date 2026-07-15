# Details

<table>
    <tr>
        <td>Name</td>
        <td>Phone and Messaging Storage</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.providers.telephony</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.16</td>
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
        <td>$200</td>
    </tr>
</table>

# Description

Oversecured found in the Phone and Messaging Storage app in the file `com/android/providers/telephony/SmsProvider.java` logging messages from the SIM card:
![](Oversecured_report.png)

**Proof of Concept**

```
adb logcat -s "TP/SmsProvider"
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.