package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class NotExported extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("evil", "Hello from NotExported");
    }
}