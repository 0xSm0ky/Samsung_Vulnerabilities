package poc.app;

import android.app.Activity;
import android.os.Bundle;

public class InterceptActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DumpUtils.dump(getIntent(), getClassLoader());
        finish();
    }
}