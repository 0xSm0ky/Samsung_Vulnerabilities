# Details

<table>
    <tr>
        <td>Name</td>
        <td>Accessibility</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.accessibility</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.20</td>
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

Oversecured found in the Accessibility app in the exported activity `com.samsung.accessibility.shortcut.MuteAllShortcut` automatic enabling automuting of all sounds. As soon as the activity is launched, it enables this setting without any action from the user.

**Proof of Concept**

```java
startActivity(new Intent().setClassName("com.samsung.accessibility", "com.samsung.accessibility.shortcut.MuteAllShortcut"));
```
