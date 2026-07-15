package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("settings.SIM_CARD_NETWORK");
        i.putExtra("root_key", "SIMCARD_ESIM_ADD_MOBILE_PLAN");
        i.putExtra("DOWNLOAD_REQUEST_FROM", 1);
        startActivity(i);
    }
}