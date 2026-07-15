# Details

<table>
    <tr>
        <td>Name</td>
        <td>Call settings</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.app.telephonyui</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.19</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.01.04</td>
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
        <td>$250</td>
    </tr>
</table>

# Description

Oversecured report:
![](Oversecured_report.png)

Oversecured found in the Call settings app in the `com/samsung/android/app/telephonyui/netsettings/ui/simcardmanager/SimCardMgrActivity.java` file processing of different UIs through the `root_key` parameter. If the attacker provides the `SIMCARD_ESIM_ADD_MOBILE_PLAN` value, the fragment `com.samsung.android.app.telephonyui.netsettings.ui.preference.EsimAddPlanFragment` will be launched. At launch, it will automatically switch the device to use eSIM if it's present on the device.

**Proof of Concept**

```java
Intent i = new Intent("settings.SIM_CARD_NETWORK");
i.putExtra("root_key", "SIMCARD_ESIM_ADD_MOBILE_PLAN");
i.putExtra("DOWNLOAD_REQUEST_FROM", 1);
startActivity(i);
```

## Conclusion

Mobile app security is more critical than ever in today's digital landscape. As we have shown through our research, even the most reputable brands are not immune to the threat of cyberattacks. 

At Oversecured, we are committed to help our clients stay ahead of the curve with our industry-leading mobile app vulnerability scanning technology. With our comprehensive approach to mobile app security, you can trust that your brand and your users are protected from data breaches and other security threats.

Don't wait until it's too late. [Get a free consultation](https://oversecured.com/contact-us?utm_source=github&utm_medium=article&utm_campaign=samsung2022) and find out how we can help secure your mobile apps. Together, we can build a safer digital world.