# Details

<table>
    <tr>
        <td>Name</td>
        <td>NFC</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.nfc</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.15</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.01.04</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21427">CVE-2023-21427</a> (SVE-2022-2280)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$450</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/114.%20NFC%20—%20Korea%20and%20China%20-%20Enabling%20NDEF%20pushes%20and%20reader/Oversecured_report.png)

Oversecured found in the NFC app patched by Samsung, in the file `com/samsung/android/nfc/quicktile/NfcTile.java` dynamically registered unprotected receiver on Korean and Chinese devices. When processing the `com.samsung.android.nfc.modestandard` action, it automatically calls `INfcAdapter.readerEnable()` and `INfcAdapter.enableNdefPush()`. These actions require administrator permissions.

**Proof of Concept**

```java
sendBroadcast(new Intent("com.samsung.android.nfc.modestandard"));
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
