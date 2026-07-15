package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("");
        i.setClassName("com.android.settings", "com.android.settings.bluetooth.DevicePickerActivity");
        startActivity(i);
    }
}