package poc.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import java.io.OutputStream;

public class InterceptActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getClipData().getItemAt(0).getUri();
        try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
            outputStream.write("test".getBytes());
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
