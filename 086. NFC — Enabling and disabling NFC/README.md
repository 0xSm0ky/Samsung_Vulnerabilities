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
        <td>2022.12.06</td>
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
        <td>$200</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/086.%20NFC%20—%20Enabling%20and%20disabling%20NFC/Oversecured_report.png)

Oversecured found a dynamic registration of an unprotected receiver in the NFC app in the `com/samsung/android/nfc/NfcFindMyMobile.java` file. It handled the following actions:
- `com.samsung.internal.policy.impl.Keyguard.PCW_UNLOCKED`, enables NFC
- `com.samsung.internal.policy.impl.Keyguard.PCW_LOCKED`, disables NFC

**Proof of Concept**

Enabling NFC:
```java
sendBroadcast(new Intent("com.samsung.internal.policy.impl.Keyguard.PCW_UNLOCKED"));
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
