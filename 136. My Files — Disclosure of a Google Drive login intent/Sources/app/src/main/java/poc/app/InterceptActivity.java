package poc.app;

import android.app.Activity;
import android.os.Bundle;

public class InterceptActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DumpUtils.dump(getIntent(), getForeignClassLoader(getCallingPackage()));
        finish();
    }

    private ClassLoader getForeignClassLoader(String packageName) {
        try {
            return createPackageContext(packageName, CONTEXT_INCLUDE_CODE | CONTEXT_IGNORE_SECURITY)
                    .getClassLoader();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}